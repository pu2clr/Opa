//
//  OpsService.m
//  Ops
//
//  Created by Ricardo Caratti on 30/09/11.
//  Copyright (c) 2011 __MyCompanyName__. All rights reserved.
//

#import <SystemConfiguration/SCNetworkReachability.h>

#import "OpsService.h"
#import "JSON.h"

@implementation OpsService


+(BOOL)canIConnect
{   
    /*
     BOOL connected;
     const char *host = "www.apple.com";
     SCNetworkReachabilityRef reachability = SCNetworkReachabilityCreateWithName(NULL, host);
     SCNetworkReachabilityFlags flags;
     connected = SCNetworkReachabilityGetFlags(reachability, &flags);
     BOOL isConnected = connected &&	(flags & kSCNetworkFlagsReachable) && !(flags & kSCNetworkFlagsConnectionRequired);
     CFRelease(reachability);
     
     return isConnected;
     */
    return YES;
}


+(NSString *) deviceStatus
{
    // Obtem a identificação do dispositivo móvel
    NSString*  deviceId = [self getDeviceId];
    
    NSMutableString *urlString = [[NSMutableString alloc] initWithString:OPA_URL];
    [urlString appendFormat:@"statusdevice/deviceId/%@",deviceId];
    
    NSString *strEncoded = [urlString stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding];
    
    NSMutableURLRequest *request = [[[NSMutableURLRequest alloc] init] autorelease];
    [request setURL:[NSURL URLWithString:strEncoded]];
    [request setHTTPMethod:@"GET"];
    [request addValue:@"application/json" forHTTPHeaderField:@"Content-Type"];
    [request addValue:@"application/json" forHTTPHeaderField:@"Accept"];    
    
    NSURLResponse *response;
    NSError *error;
    
    NSData *returnData = [NSURLConnection sendSynchronousRequest:request returningResponse:&response error:&error];
    NSString *jsonString = [[[NSString alloc] initWithData:returnData encoding:NSUTF8StringEncoding] autorelease];
    
    NSString *jsonResult = (NSString *) [[jsonString JSONValue] objectForKey:@"result"];
    
    
    [urlString autorelease];
    
    // return jsonString;
    return jsonResult;
}

+(NSString *) toActivate: (NSString *) typedCode
{
    
    // Obtem a identificação do dispositivo móvel
    NSString*  deviceId = [self getDeviceId];
    
    NSMutableString *urlString = [[NSMutableString alloc] initWithString:OPA_URL];
    
    [urlString appendFormat:@"activedevice/deviceId/%@/typedCode/%@",deviceId,typedCode];
    
    NSString *strEncoded = [urlString stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding];
    
    NSMutableURLRequest *request = [[[NSMutableURLRequest alloc] init] autorelease];
    [request setURL:[NSURL URLWithString:strEncoded]];
    [request setHTTPMethod:@"GET"];
    [request addValue:@"application/json" forHTTPHeaderField:@"Content-Type"];
    [request addValue:@"application/json" forHTTPHeaderField:@"Accept"];    
    
    NSURLResponse *response;
    NSError *error;
    
    NSData *returnData = [NSURLConnection sendSynchronousRequest:request returningResponse:&response error:&error];
    NSString *jsonString = [[[NSString alloc] initWithData:returnData encoding:NSUTF8StringEncoding] autorelease];
    
    NSString *jsonResult = (NSString *) [[jsonString JSONValue] objectForKey:@"result"];
    
    
    [urlString release];
    
    // return jsonString;
    
    return jsonResult;
}

/**
 Registra um dispositivo móvel.
 Esta função é a primeira etapa do processo de registro de um dispositivo móvel para utilização do serviço.
 
 
 
 */
+(NSString *) deviceRegister: (NSString *) userName email: (NSString *) email ddd: (NSString *) ddd phoneNumber: (NSString *) phoneNumber
{
    // Obtem a identificação do dispositivo móvel
    NSString*  deviceId = [self getDeviceId];
    
    NSString*  osName = [[UIDevice currentDevice] systemName];
    NSString*  osVersion = [[UIDevice currentDevice] systemVersion];
    
    NSString*  deviceName = [[UIDevice currentDevice] name];
    
    NSMutableString *urlString = [[NSMutableString alloc] initWithString:OPA_URL];
    
    [urlString appendFormat:@"registerdevice/deviceId/%@/osName/%@/osVersion/%@/deviceName/%@/userName/%@/email/%@/phoneDDD/%@/phoneNumber/%@",deviceId, osName, osVersion, deviceName, userName, email, ddd, phoneNumber];
    
    
    NSString *strEncoded = [urlString stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding];
    
    
    NSMutableURLRequest *request = [[[NSMutableURLRequest alloc] init] autorelease];
    [request setURL:[NSURL URLWithString:strEncoded]];
    [request setHTTPMethod:@"GET"];
    [request addValue:@"application/json" forHTTPHeaderField:@"Content-Type"];
    [request addValue:@"application/json" forHTTPHeaderField:@"Accept"];    
    
    NSURLResponse *response;
    NSError *error;
    
    NSData *returnData = [NSURLConnection sendSynchronousRequest:request returningResponse:&response error:&error];
    NSString *jsonString = [[[NSString alloc] initWithData:returnData encoding:NSUTF8StringEncoding] autorelease];
    
    NSString *jsonResult = (NSString *) [[jsonString JSONValue] objectForKey:@"result"];
    
    [urlString release];
    
    // return jsonString;
    return jsonResult;
}



// Obtem os serviços mais utilizados
+(NSMutableArray *) getTopServices 
{
    
    NSMutableString *urlString = [[[NSMutableString alloc] initWithString:OPA_URL] autorelease];
    [urlString appendString:@"topservices"];
    
    NSMutableURLRequest *request = [[[NSMutableURLRequest alloc] init] autorelease];
    [request setURL:[NSURL URLWithString:urlString]];
    [request setHTTPMethod:@"GET"];
    [request addValue:@"application/json" forHTTPHeaderField:@"Content-Type"];
    [request addValue:@"application/json" forHTTPHeaderField:@"Accept"];
    
    NSData *returnData = [NSURLConnection sendSynchronousRequest:request returningResponse:nil error:nil];    
    
    NSString *jsonString = [[[NSString alloc] initWithData:returnData encoding:NSUTF8StringEncoding] autorelease];
    
    NSMutableArray *jsonArray; // = [NSMutableArray array];
    
    jsonArray = [(NSDictionary *) [jsonString JSONValue] objectForKey:@"topservices"];
    
    NSMutableDictionary *dict;
    
    int k = [jsonArray count];
    
    for (int i = 0; i < k; i++) {
        
        dict = [jsonArray objectAtIndex:i];
        
        NSString *urlImage = [[NSString alloc] initWithFormat:@"%@%@",OPA_IMAGE_PATH,[dict objectForKey:@"icon_name"]];
        
        NSURL *url = [[[NSURL alloc] initWithString:urlImage] autorelease];
        
        NSData* imageData = [[[NSData alloc] initWithContentsOfURL:url] autorelease];
        UIImage* image = [[[UIImage alloc] initWithData:imageData] autorelease];
        
        [dict setValue:image forKey:@"image_data"];
        [urlImage autorelease];
    }
    
    // [jsonArray writeToFile:@"topservices.plist" atomically:YES];
    //  NSDictionary *dict=[NSDictionary dictionaryWithContentsOfFile:@"topservices.plist"];

    return  jsonArray;
}


+(NSMutableArray *) getAllServices
{
    NSMutableString *urlString = [[[NSMutableString alloc] initWithString:OPA_URL] autorelease];    
    [urlString appendString:@"allservices"];
    
    NSMutableURLRequest *request = [[[NSMutableURLRequest alloc] init] autorelease];
    [request setURL:[NSURL URLWithString:urlString]];
    [request setHTTPMethod:@"GET"];
    [request addValue:@"application/json" forHTTPHeaderField:@"Content-Type"];
    [request addValue:@"application/json" forHTTPHeaderField:@"Accept"];
    
    NSData *returnData = [NSURLConnection sendSynchronousRequest:request returningResponse:nil error:nil];    
    
    NSString *jsonString = [[[NSString alloc] initWithData:returnData encoding:NSUTF8StringEncoding] autorelease];
    
    NSMutableArray *jsonArray; //  = [NSMutableArray array];
    
    jsonArray = [(NSDictionary *) [jsonString JSONValue] objectForKey:@"allservices"];
    
    
    NSMutableDictionary *dict;
    
    int k = [jsonArray count];
    
    for (int i = 0; i < k; i++) {
        
        dict = [jsonArray objectAtIndex:i];
        
        NSString *urlImage = [[[NSString alloc] initWithFormat:@"%@%@",OPA_IMAGE_PATH,[dict objectForKey:@"icon_name"]] autorelease];
        
        NSURL *url = [[[NSURL alloc] initWithString:urlImage] autorelease];
        
        NSData* imageData = [[[NSData alloc] initWithContentsOfURL:url] autorelease];
        UIImage* image = [[[UIImage alloc] initWithData:imageData] autorelease];
        
        [dict setValue:image forKey:@"image_data"];
        
    }
    
    [jsonArray writeToFile:@"allservices.plist" atomically:YES];
    
    return  jsonArray;
    
}

// Registra um ocorrência ou uma demanda
+(NSString *) sendOps:  (NSString *) latitude longitude: (NSString *) longitude messageType: (NSInteger) messageType message:(NSString *)message mediaType:(NSInteger)mediaType fileName:(NSString *)fileName;
{
    
    // Obtem a identificação do dispositivo móvel
    NSString*  deviceId = [self getDeviceId];
    
    NSMutableString *urlString = [[[NSMutableString alloc] initWithString:OPA_URL] autorelease];        
    
    [urlString appendFormat:@"requestservice/deviceId/%@/latitude/%@/longitude/%@/serviceId/%d/message/%@/mediaType/%d/fileName/%@",deviceId,latitude,longitude, messageType, message,mediaType,fileName];
    
    NSString *strEncoded = [urlString stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding];
    
    NSMutableURLRequest *request = [[[NSMutableURLRequest alloc] init] autorelease];
    [request setURL:[NSURL URLWithString:strEncoded]];
    [request setHTTPMethod:@"GET"];
    [request addValue:@"application/json" forHTTPHeaderField:@"Content-Type"];
    [request addValue:@"application/json" forHTTPHeaderField:@"Accept"];    
    
    NSURLResponse *response;
    NSError *error;
    
    NSData *returnData = [NSURLConnection sendSynchronousRequest:request returningResponse:&response error:&error];
    NSString *jsonString = [[[NSString alloc] initWithData:returnData encoding:NSUTF8StringEncoding] autorelease];
    
    NSString *jsonResult = (NSString *) [[jsonString JSONValue] objectForKey:@"result"];
    
    
    // return jsonString;
    return jsonResult;
}


+(NSString *) resetServices
{
    NSMutableString *urlString = [[[NSMutableString alloc] initWithString:OPA_URL] autorelease];
    [urlString appendString:@"reset"];    
    
    NSString *strEncoded = [urlString stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding];
    
    NSMutableURLRequest *request = [[[NSMutableURLRequest alloc] init] autorelease];
    [request setURL:[NSURL URLWithString:strEncoded]];
    [request setHTTPMethod:@"PUT"];
    [request addValue:@"application/json" forHTTPHeaderField:@"Content-Type"];
    [request addValue:@"application/json" forHTTPHeaderField:@"Accept"];    
    
    NSURLResponse *response;
    NSError *error;
    
    NSData *returnData = [NSURLConnection sendSynchronousRequest:request returningResponse:&response error:&error];
    NSString *jsonString = [[[NSString alloc] initWithData:returnData encoding:NSUTF8StringEncoding] autorelease];
    
    NSString *jsonResult = (NSString *) [[jsonString JSONValue] objectForKey:@"result"];
    
    
    // return jsonString;
    return jsonResult;
    
}

// Retorna o nome do arquivo enviado
+(NSString *) uploadMedia:(NSData *)media 
{
    
    
    NSDate *now = [NSDate date];
    NSDateFormatter *dateFormat = [[[NSDateFormatter alloc] init] autorelease];
    [dateFormat setDateFormat:@"yyyy-MMM-dd-HH-mm-ss"];
    NSString *strDate = [dateFormat stringFromDate:now]; 
    
    NSString*  deviceId = [self getDeviceId];
    
    NSString *filename = [NSString stringWithFormat:@"iOS_%@_X_%@.jpg",deviceId,strDate];
    
    
    NSMutableURLRequest *request= [[[NSMutableURLRequest alloc] init] autorelease];
    [request setURL:[NSURL URLWithString:OPA_UPLOAD_IMAGE_PATH]];
    [request setHTTPMethod:@"POST"];
    NSString *boundary = @"---------------------------14737809831466499882746641449";
    NSString *contentType = [NSString stringWithFormat:@"multipart/form-data; boundary=%@",boundary];
    [request addValue:contentType forHTTPHeaderField: @"Content-Type"];
    NSMutableData *postbody = [NSMutableData data];
    [postbody appendData:[[NSString stringWithFormat:@"\r\n--%@\r\n",boundary] dataUsingEncoding:NSUTF8StringEncoding]];
    [postbody appendData:[[NSString stringWithFormat:@"Content-Disposition: form-data; name=\"file\"; filename=\"%@\"\r\n", filename] dataUsingEncoding:NSUTF8StringEncoding]];
    [postbody appendData:[@"Content-Type: application/octet-stream\r\n\r\n" dataUsingEncoding:NSUTF8StringEncoding]];
    [postbody appendData:[NSData dataWithData:media]];
    [postbody appendData:[[NSString stringWithFormat:@"\r\n--%@--\r\n",boundary] dataUsingEncoding:NSUTF8StringEncoding]];
    [request setHTTPBody:postbody];
    
    [NSURLConnection sendSynchronousRequest:request returningResponse:nil error:nil];
    

    return filename;

}


/*
 saveId
 Quando um usuario se registra, um numero eh criado para identificar o usuario por meio de UIDevice
 
 Para contornar isso, o sistema gera um Id para identificar o usuario.
 
 */
+(BOOL) saveId:(NSInteger)userId
{
 
    return YES;
}


+(NSInteger) getUserId
{
    
    return 0;
}

+(NSString *) getDeviceId
{
   return [[UIDevice currentDevice] uniqueIdentifier];
}


@end
