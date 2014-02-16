//
//  DeviceActivationVewController.h
//  Opa
//
//  Created by Ricardo Caratti on 04/10/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface DeviceActivationVewController : UIViewController
{
    IBOutlet UITextField *typedCode;
}

@property (nonatomic, retain) IBOutlet UITextField *typedCode;


-(IBAction)doActivate;


@end
