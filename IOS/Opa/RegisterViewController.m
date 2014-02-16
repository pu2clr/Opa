//
//  RegisterViewController.m
//  Opa
//
//  Created by Ricardo Caratti on 03/10/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//


#import "RegisterViewController.h"
#import "OpsService.h"
#import "DeviceActivationVewController.h"


@implementation RegisterViewController

@synthesize email, ddd, phoneNumber, dddConfirmation, phoneNumberConfirmation, userName;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}


-(IBAction)doRegister
{
    
    if ( ![ddd.text isEqualToString:dddConfirmation.text] ) {
        UIAlertView *alert = [[[UIAlertView alloc] initWithTitle:@"Erro" message: @"O número do DDD não confere." delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil] autorelease];
        [alert show];  
        return;
        
    }
    
    if ( ![phoneNumber.text isEqualToString:phoneNumberConfirmation.text]) {
        UIAlertView *alert = [[[UIAlertView alloc] initWithTitle:@"Erro" message: @"O número do telefone não confere." delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil] autorelease];
        [alert show];
        return;
        
    }
    
    NSString *returnMessage = [OpsService deviceRegister:userName.text email:email.text ddd: ddd.text phoneNumber:phoneNumber.text];
    
    if ( [returnMessage isEqualToString:@"Pending"] ) {
        NSString *showMessage = [[NSString alloc] initWithFormat:@"Dispositivo registrado com sucesso. Você receberá uma solicitação de confirmação de seu registro. A sua situação atual é: %@", returnMessage]; 
          UIAlertView *alert = [[[UIAlertView alloc] initWithTitle:@"Observação" message: showMessage delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil] autorelease];
        [alert show];
        [showMessage release];
        
        DeviceActivationVewController *deviceActivation = [[[DeviceActivationVewController alloc] initWithNibName:@"DeviceActivationVewController" bundle:nil] autorelease];
        
        [self.navigationController pushViewController:deviceActivation animated:YES];
     
        
    }
    else {
        UIAlertView *alert = [[[UIAlertView alloc] initWithTitle:@"Erro" message: returnMessage delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil] autorelease];
        [alert show];        
    }

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
    userName = nil;
    email = nil;
    phoneNumber = nil;
    ddd = nil;
    dddConfirmation = nil;
    phoneNumberConfirmation = nil;

}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

-(void) dealloc
{
    [userName release];
    [email release];
    [phoneNumber release];
    [phoneNumberConfirmation release];
    [ddd release];
    [dddConfirmation release];
    [super dealloc];
}

@end
