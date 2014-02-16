//
//  SendOps.h
//  Opa
//
//  Created by Ricardo Caratti on 01/10/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <MobileCoreServices/UTCoreTypes.h>
#import <CoreLocation/CoreLocation.h>

@interface SendOpsViewController : UIViewController <UIImagePickerControllerDelegate, UINavigationControllerDelegate>
{
    NSString *serviceName;
    NSString *serviceId;
    NSString *fileNameUploaded;
    
    NSInteger  mediaType;
    NSData    *mediaContent;
    
    CLLocationDegrees latitude;
    CLLocationDegrees longitude;
    
    IBOutlet UITextView *textViewMessage;
    IBOutlet UILabel *labelMessage;
    IBOutlet UIImageView *imegePreview;
}

@property (nonatomic, retain) NSString *serviceName;;
@property (nonatomic, retain) NSString *serviceId;
@property (nonatomic, retain) NSString *fileNameUploaded;
@property  NSInteger mediaType;
@property (nonatomic, retain) NSData   *mediaContent;


@property (nonatomic, readwrite) CLLocationDegrees latitude;
@property (nonatomic, readwrite) CLLocationDegrees longitude;

@property (nonatomic, retain) IBOutlet UITextView *textViewMessage;
@property (nonatomic, retain) IBOutlet UILabel *labelMessage;
@property (nonatomic, retain) IBOutlet UIImageView *imegePreview;

-(void) showMessage: (NSString *) msg title: (NSString *) title;


-(IBAction) sendOps;
-(IBAction) takePicture;
-(IBAction) recordMessage;
-(IBAction) recordVideo;

-(IBAction) hideKeyboard:(id)sender;


@end
