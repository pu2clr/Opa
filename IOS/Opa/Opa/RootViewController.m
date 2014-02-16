//
//  RootViewController.m
//  Opa
//
//  Created by Ricardo Caratti on 30/09/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "RootViewController.h"
#import "OpsService.h"
#import "SendOpsViewController.h"
#import "RegisterViewController.h"
#import "DeviceActivationVewController.h"

@implementation RootViewController

@synthesize tableViewDataSource, titleSections, latitude, longitude, locationManager, tableView;

- (id)initWithStyle:(UITableViewStyle)style
{
    self = [super initWithStyle:style];
    if (self) {
        // Custom initialization
    }
    return self;
}

/**
 Exibe uma alerta na tela do dispositivo
 
 @Param msg     Mensagem a ser exibida
*/

-(void) showMessage: (NSString *) msg title: (NSString *) title
{
     UIAlertView *alert = [[UIAlertView alloc] initWithTitle:title message: msg delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil, nil];
    
    [alert autorelease];
    [alert show];
}


- (void)viewDidLoad
{
    [super viewDidLoad];
    
    
    // [[self view] setBackgroundColor:[UIColor orangeColor]];
    
    
    NSString *statusDevice = [OpsService deviceStatus];
    
    
    
    if ( ![OpsService canIConnect] ) {
        [self showMessage:@"Serviço indisponível! Verifiquese se este dispositivo está conectado a Internet." title:@"Atenção"];
    } 
    else if ( [statusDevice isEqualToString:@"Device not found"] ) {
        [self showMessage:@"Este dispositivo ainda não foi registrado!" title:@"Atenção"]; 
        
        
        RegisterViewController *registerVC = [[RegisterViewController alloc] initWithNibName:@"RegisterViewController" bundle:nil]; 
        
        
        [self.navigationController pushViewController: registerVC animated: YES];
        
        [registerVC release];
        registerVC = nil;       
    } 
    else if ( ![statusDevice isEqualToString:@"Activated"]) {
        [self showMessage:@"Dispositivo registrado, mas, ainda não ativado! Favor confirme o registro de seu dispositivo informando o código de ativação!" title:@"Atenção"]; 
        
        DeviceActivationVewController *deviceActivation = [[DeviceActivationVewController alloc] initWithNibName:@"DeviceActivationVewController" bundle:nil];
        
        [self.navigationController pushViewController:deviceActivation animated:YES];
        [deviceActivation release];
        deviceActivation = nil;
        
    }
    
    self.locationManager = [[[CLLocationManager alloc] init] autorelease];
    [self.locationManager setDelegate:self];
    [self.locationManager setDesiredAccuracy:kCLLocationAccuracyBest];
    [self.locationManager startUpdatingLocation];
    
    tableViewDataSource = [[NSMutableArray alloc] initWithCapacity:0];
    titleSections = [[NSMutableArray alloc] initWithCapacity:0];
    
    // Obtem os serviços mais utilizados
    [titleSections addObject:@"Mais solicitados"];
    [tableViewDataSource addObject: [OpsService getTopServices]];  
    
    // Obtem os demais serviços (Todos menos os mais utilizados)
    [titleSections addObject:@"Demais serviços"];
    [tableViewDataSource addObject:[OpsService getAllServices]];
    
    
    
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
   
}

- (void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:animated];
}

- (void)viewWillDisappear:(BOOL)animated
{
	[super viewWillDisappear:animated];
}

- (void)viewDidDisappear:(BOOL)animated
{
	[super viewDidDisappear:animated];
}


-(void)locationManager:(CLLocationManager *)manager didUpdateToLocation:(CLLocation *)newLocation fromLocation:(CLLocation *)oldLocation
{
    static int count = 0;
    
    if ( count++ > 3 ) {
        [locationManager stopUpdatingHeading];
        count = 0;
        return;
    }
    
    latitude =   newLocation.coordinate.latitude;
    longitude =  newLocation.coordinate.longitude;
}

-(void)locationManager:(CLLocationManager *)manager didFailWithError:(NSError *)error
{
    NSString *erroTipo = (error.code == kCLErrorDenied) ? @"Acesso Negado" : @"Erro Desconhecido";
    
    UIAlertView *alerta = [[UIAlertView alloc] initWithTitle:@"Erro ao Recuperar a Localização" message:erroTipo delegate:nil cancelButtonTitle:@"Ok" otherButtonTitles:nil, nil];
    [alerta autorelease];
    [alerta show];
}


/*
 // Override to allow orientations other than the default portrait orientation.
- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
	// Return YES for supported orientations.
	return (interfaceOrientation == UIInterfaceOrientationPortrait);
}
 */


// Customize the number of sections in the table view.
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return [titleSections count];
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    // Retorna o número de elementos do dicionário contido em tableViewDataSource(section)
    return [[tableViewDataSource objectAtIndex:section] count];
}

-(NSString *) tableView:(UITableView *)tableView titleForHeaderInSection:(NSInteger)section
{
    // Informa o tótulo da seção
    return [titleSections objectAtIndex:section];
}

// Customize the appearance of table view cells.
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *CellIdentifier = @"Cell";
    
    UIImage *navBarImage = [UIImage imageNamed:@"background.png"];
    
    tableView.backgroundColor = [UIColor colorWithPatternImage:navBarImage];
    
    
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    if (cell == nil) {
        cell = [[[UITableViewCell alloc] initWithStyle:UITableViewCellStyleSubtitle reuseIdentifier:CellIdentifier] autorelease];
    }
    
    NSMutableArray *dict = [tableViewDataSource objectAtIndex: [indexPath section]];
    cell.textLabel.text = [[dict objectAtIndex:[indexPath row]] objectForKey:@"service_name"]; 
    cell.detailTextLabel.text = [[dict objectAtIndex:[indexPath row]] objectForKey:@"description"];
    
    cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
    cell.imageView.image = [[dict objectAtIndex:[indexPath row]] objectForKey:@"image_data"];
    
    return cell;
}

/*
// Override to support conditional editing of the table view.
- (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the specified item to be editable.
    return YES;
}
*/

/*
// Override to support editing the table view.
- (void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (editingStyle == UITableViewCellEditingStyleDelete)
    {
        // Delete the row from the data source.
        [tableView deleteRowsAtIndexPaths:[NSArray arrayWithObject:indexPath] withRowAnimation:UITableViewRowAnimationFade];
    }
    else if (editingStyle == UITableViewCellEditingStyleInsert)
    {
        // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view.
    }   
}
*/

/*
// Override to support rearranging the table view.
- (void)tableView:(UITableView *)tableView moveRowAtIndexPath:(NSIndexPath *)fromIndexPath toIndexPath:(NSIndexPath *)toIndexPath
{
}
*/

/*
// Override to support conditional rearranging of the table view.
- (BOOL)tableView:(UITableView *)tableView canMoveRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the item to be re-orderable.
    return YES;
}
*/

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    [locationManager startUpdatingLocation];
    
    NSMutableArray *dict = [tableViewDataSource objectAtIndex: [indexPath section]];
    
        
    SendOpsViewController *sendOps = [[SendOpsViewController alloc] initWithNibName:@"SendOpsViewController" bundle:nil]; 
        
    sendOps.serviceId = [[dict objectAtIndex:[indexPath row]] objectForKey:@"service_id"];
    sendOps.serviceName = [[dict objectAtIndex:[indexPath row]] objectForKey:@"service_name"];
    sendOps.latitude = self.latitude;
    sendOps.longitude = self.longitude;
    
    [self.navigationController pushViewController: sendOps animated: YES];
        
    [sendOps release];
    sendOps = nil;
    
}

- (void)didReceiveMemoryWarning
{
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Relinquish ownership any cached data, images, etc that aren't in use.
}

- (void)viewDidUnload
{
    [super viewDidUnload];

    // Relinquish ownership of anything that can be recreated in viewDidLoad or on demand.
    // For example: self.myOutlet = nil;
    
    titleSections = nil;
    tableViewDataSource = nil;
    self.locationManager = nil;
}

- (void)dealloc
{
    [locationManager release];
    [titleSections release];
    [tableViewDataSource release];
    [super dealloc];
}

@end
