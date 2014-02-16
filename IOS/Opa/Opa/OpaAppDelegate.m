//
//  OpaAppDelegate.m
//  Opa
//
//  Created by Ricardo Caratti on 30/09/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "OpaAppDelegate.h"

@implementation OpaAppDelegate

@synthesize window = _window;
@synthesize navigationController = _navigationController;

// Inserido para tratar notificação Push
- (void) applicationDidFinishLaunching:(UIApplication *)application {
    application.applicationIconBadgeNumber = 0;
    // update data from Server.b
}

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{

    // Informa ao dispositivo móvel que esta aplicação irá receber notificação via Push.
    [[UIApplication sharedApplication] registerForRemoteNotificationTypes:(UIRemoteNotificationTypeAlert | UIRemoteNotificationTypeBadge | UIRemoteNotificationTypeSound)];
    
    // Personaliza alguns componentes deinterface. Veja método customizeTheme a seguir.
    [self customizeTheme];
    
    self.window.rootViewController = self.navigationController;
    [self.window makeKeyAndVisible];
    return YES;
}

- (void)application:(UIApplication *) app didFailToRegisterForRemoteNotificationsWithError:(NSError *)error {
    
    NSLog(@"%@ - Erro ao registrar esta aplicação para Notificações via Push.", error);
}

-(void)application: (UIApplication *) app didRegisterForRemoteNotificationsWithDeviceToken:(NSData *)deviceToken {
    
    [self sendProviderDeviceToken: deviceToken];
    
}

// Envia o token para o meu provedor de push
-(void) sendProviderDeviceToken:(NSData *) deviceToken {
    
}
     
- (void)customizeTheme
{
    UIImage *navBarImage = [UIImage imageNamed:@"menu-bar.png"];
     
    [[UINavigationBar appearance] setBackgroundImage:navBarImage 
                                     forBarMetrics:UIBarMetricsDefault];
    
    
    UIImage *barButton = [UIImage imageNamed:@"menu-bar-button.png"];
    
    [[UIBarButtonItem appearance] setBackgroundImage:barButton forState:UIControlStateNormal 
                                          barMetrics:UIBarMetricsDefault];
    
    UIImage *backButton = [UIImage imageNamed:@"Back-no-text.png"];
    
    [[UIBarButtonItem appearance] setBackButtonBackgroundImage:backButton forState:UIControlStateNormal 
                                                    barMetrics:UIBarMetricsDefault];
    
    UIImage* tabBarBackground = [UIImage imageNamed:@"tab-bar.png"];
    
    [[UITabBar appearance] setBackgroundImage:tabBarBackground];
    
    UIImage *minImage = [UIImage imageNamed:@"slider-background.png"];
    UIImage *maxImage = [UIImage imageNamed:@"slider-fill.png"];
    UIImage *thumbImage = [UIImage imageNamed:@"slider-button.png"];
    
    [[UISlider appearance] setMaximumTrackImage:maxImage 
                                       forState:UIControlStateNormal];
    [[UISlider appearance] setMinimumTrackImage:minImage 
                                       forState:UIControlStateNormal];
    [[UISlider appearance] setThumbImage:thumbImage 
                                forState:UIControlStateNormal];
    
}


- (void)applicationWillResignActive:(UIApplication *)application
{
    /*
     Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
     Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
     */
}

- (void)applicationDidEnterBackground:(UIApplication *)application
{
    /*
     Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later. 
     If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
     */
    
    // exit(0);
}

- (void)applicationWillEnterForeground:(UIApplication *)application
{
    /*
     Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
     */
}

- (void)applicationDidBecomeActive:(UIApplication *)application
{
    /*
     Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
     */
}

- (void)applicationWillTerminate:(UIApplication *)application
{
    /*
     Called when the application is about to terminate.
     Save data if appropriate.
     See also applicationDidEnterBackground:.
     */
}

- (void)dealloc
{
    [_window release];
    [_navigationController release];
    [super dealloc];
}

@end
