//
//  DeviceActivationVewController.m
//  Opa
//
//  Created by Ricardo Caratti on 04/10/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "DeviceActivationVewController.h"
#import "OpsService.h"

@implementation DeviceActivationVewController

@synthesize typedCode;


-(IBAction)doActivate
{
    NSString *result = [OpsService toActivate:typedCode.text];
    
    NSString *showMessage;
    
    if ( ![result isEqualToString:@"Activated"] ) {
        showMessage = [[NSString alloc] initWithFormat:@"O dispositivo não pode ser ativado: %@. Tente novamente inserindo o código de ativação correto!", result]; 
        UIAlertView *alert = [[[UIAlertView alloc] initWithTitle:@"Atenção" message: showMessage delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil] autorelease];
        [alert show];
        [showMessage release];
        return;
    }
    
    showMessage = [[NSString alloc] initWithFormat:@"O seu dispositivo foi ativado e está habilitado para utilizar nossos serviços!", result];
    UIAlertView *alert = [[[UIAlertView alloc] initWithTitle:@"Parabéns!" message: showMessage delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil] autorelease];
    
    [alert show];
    [showMessage release];  
    
    [self.navigationController popToRootViewControllerAnimated:YES];    
    
}


- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)didReceiveMemoryWarning
{
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    
    UIImage *navBarImage = [UIImage imageNamed:@"background.png"];
    
    self.view.backgroundColor = [UIColor colorWithPatternImage:navBarImage];    
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
    typedCode = nil;
}


- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

-(void) dealloc
{
    [typedCode release];
    [super dealloc];
}

@end
