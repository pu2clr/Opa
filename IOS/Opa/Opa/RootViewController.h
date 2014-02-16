//
//  RootViewController.h
//  Opa
//
//  Created by Ricardo Caratti on 30/09/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "OpsService.h"
#import <CoreLocation/CoreLocation.h>

@interface RootViewController :  UITableViewController  <CLLocationManagerDelegate>
{
   
    UITableView *tableView;
    
    NSMutableArray *tableViewDataSource;
    NSMutableArray *titleSections;
    
    CLLocationDegrees latitude;
    CLLocationDegrees longitude;
    
    CLLocationManager *locationManager;
    
}

@property (nonatomic, retain) NSMutableArray *tableViewDataSource;
@property (nonatomic, retain) NSMutableArray *titleSections;


@property (nonatomic, readwrite) CLLocationDegrees latitude;
@property (nonatomic, readwrite) CLLocationDegrees longitude;
@property (nonatomic, retain) CLLocationManager *locationManager;

@property (nonatomic, retain) UITableView *tableView;


-(void) showMessage: (NSString *) msg title: (NSString *) title;

@end
