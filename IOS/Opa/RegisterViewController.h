//
//  RegisterViewController.h
//  Opa
//
//  Created by Ricardo Caratti on 03/10/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface RegisterViewController : UIViewController
{
    IBOutlet UITextField *userName;
    IBOutlet UITextField *ddd;
    IBOutlet UITextField *phoneNumber;
    IBOutlet UITextField *dddConfirmation;    
    IBOutlet UITextField *phoneNumberConfirmation;
    IBOutlet UITextField *email;
    
   
}

@property(nonatomic, retain) IBOutlet UITextField *userName;
@property(nonatomic, retain) IBOutlet UITextField *ddd;
@property(nonatomic, retain) IBOutlet UITextField *phoneNumber;
@property(nonatomic, retain) IBOutlet UITextField *dddConfirmation;
@property(nonatomic, retain) IBOutlet UITextField *phoneNumberConfirmation;
@property(nonatomic, retain) IBOutlet UITextField *email;


-(IBAction) doRegister;




@end
