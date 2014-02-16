//
//  OpaAppDelegate.h
//  Opa
//
//  Created by Ricardo Caratti on 30/09/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface OpaAppDelegate : NSObject <UIApplicationDelegate>

@property (nonatomic, retain) IBOutlet UIWindow *window;

@property (nonatomic, retain) IBOutlet UINavigationController *navigationController;

- (void)customizeTheme;
- (void) sendProviderDeviceToken:(NSData *) deviceToken;
@end
