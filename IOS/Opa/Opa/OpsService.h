//
//  OpsService.h
//  Ops
//
//  Created by Ricardo Caratti on 30/09/11.
//  Copyright (c) 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

static NSString *const OPA_URL = 	@"http://www.consultoriaavaliar.com.br/OpsAdmin/public/oparest/";

static NSString *const OPA_IMAGE_PATH = @"http://www.consultoriaavaliar.com.br/OpsAdmin/public/images/";

static NSString *const OPA_UPLOAD_IMAGE_PATH = @"http://www.consultoriaavaliar.com.br/OpsAdmin/public/upload/upload";


@interface OpsService : NSObject 
{

}

+(BOOL) canIConnect;

+(NSString *) toActivate: (NSString *) typedCode;
+(NSString *) deviceRegister: (NSString *) userName email: (NSString *) email ddd: (NSString *) ddd phoneNumber: (NSString *) phoneNumber;
+(NSString *) deviceStatus;

+(NSMutableArray *) getTopServices;
+(NSMutableArray *) getAllServices;

+(NSString *) sendOps: (NSString *) latitude longitude: (NSString *) longitude messageType: (NSInteger) messageType message:(NSString *)message mediaType: (NSInteger) mediaType fileName:(NSString *) fileName;

+(NSString *) resetServices;

+(NSString *) uploadMedia: (NSData *) media;

+(BOOL) saveId: (NSInteger) userId;
+(NSInteger) getUserId;
+(NSString *) getDeviceId;

@end
