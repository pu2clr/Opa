//
//  SendOps.m
//  Opa
//
//  Created by Ricardo Caratti on 01/10/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "SendOpsViewController.h"
#import "OpsService.h"

@implementation SendOpsViewController
@synthesize longitude, latitude, serviceId, serviceName, textViewMessage, labelMessage, imegePreview, fileNameUploaded, mediaType, mediaContent;

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


-(void) showMessage: (NSString *) msg title: (NSString *) title
{
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:title message: msg delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil, nil];
    
    [alert autorelease];
    [alert show];
}

-(IBAction)sendOps
{
    [super didReceiveMemoryWarning];
    NSString *strLatitude  = [[NSString alloc] initWithFormat:@"%+.6f", latitude];
    NSString *strLongitude = [[NSString alloc] initWithFormat:@"%+.6f", longitude]; 
    
    NSString *strMessage = [[NSString alloc] initWithFormat:@"%@: %@",serviceName,textViewMessage.text];
    
    if ( self.mediaContent != nil )
        self.fileNameUploaded = [OpsService uploadMedia:self.mediaContent];
    else
        self.fileNameUploaded = @"nomedia.jpg";
    
    NSString *strStatus = [OpsService sendOps:strLatitude longitude:strLongitude messageType:[serviceId intValue] message: strMessage mediaType: self.mediaType fileName: self.fileNameUploaded];
    
    if ( [strStatus isEqualToString:@"OK"] ) {
        [self showMessage:@"Solicitação registrada com sucesso!" title:@" "];
    } 
    else {
        [self showMessage:strStatus title:@"Atenção"];            
    }
    
    [self hideKeyboard:self];
    
    [strLatitude release];
    [strLongitude release];
    [strMessage release];
}


-(IBAction)recordVideo
{
    if ([UIImagePickerController isSourceTypeAvailable:UIImagePickerControllerSourceTypeCamera])
	{
        UIImagePickerController *videoRecorder = [[UIImagePickerController alloc] init];
        videoRecorder.sourceType = UIImagePickerControllerSourceTypeCamera;
        videoRecorder.delegate = self;
        
        NSArray *mediaTypes = [UIImagePickerController availableMediaTypesForSourceType:UIImagePickerControllerSourceTypeCamera];
        NSArray *videoMediaTypesOnly = [mediaTypes filteredArrayUsingPredicate:[NSPredicate predicateWithFormat:@"(SELF contains %@)", @"movie"]];
        
        if ([videoMediaTypesOnly count] == 0)		//Is movie output possible?
        {
            UIActionSheet *actionSheet = [[UIActionSheet alloc] initWithTitle:@"Seu dispositivo não possui câmera."
                                                                     delegate:nil
                                                            cancelButtonTitle:@"OK"
                                                       destructiveButtonTitle:nil
                                                            otherButtonTitles:nil];
            [actionSheet showInView:[[self view] window]];
            [actionSheet autorelease];
        }
        else
        {
            //Select front facing camera if possible
            if ([UIImagePickerController isCameraDeviceAvailable:UIImagePickerControllerCameraDeviceFront])
                videoRecorder.cameraDevice = UIImagePickerControllerCameraDeviceFront;
            
            videoRecorder.mediaTypes = videoMediaTypesOnly;
            videoRecorder.videoQuality = UIImagePickerControllerQualityTypeMedium;
            videoRecorder.videoMaximumDuration = 180;			//Specify in seconds (600 is default)
            
            [self presentModalViewController:videoRecorder animated:YES];
        }
        [videoRecorder release];
    }
    else
    {
        //No camera is availble
    }    
    
}

-(IBAction)takePicture
{

    [self hideKeyboard:self];

    
    UIImagePickerController *imagePicker = [[UIImagePickerController alloc] init];
    
	//Use camera if device has one otherwise use photo library
	if ([UIImagePickerController isSourceTypeAvailable:UIImagePickerControllerSourceTypeCamera])
	{
		[imagePicker setSourceType:UIImagePickerControllerSourceTypeCamera];
        imagePicker.mediaTypes =
        [UIImagePickerController availableMediaTypesForSourceType:
         UIImagePickerControllerSourceTypeCamera];
        
        imagePicker.allowsEditing = YES;
	}
	else
	{
		[imagePicker setSourceType:UIImagePickerControllerSourceTypePhotoLibrary];
	}
    
    imagePicker.delegate =self;
    
	[self presentModalViewController:imagePicker animated:YES];
	[imagePicker release];    
    
}



- (void) imagePickerController: (UIImagePickerController *) picker
 didFinishPickingMediaWithInfo: (NSDictionary *) info {
    
    NSString *mediaType = [info objectForKey: UIImagePickerControllerMediaType];
    UIImage *originalImage, *editedImage, *imageToSave;
    
    
    
    // Handle a still image capture
    if (CFStringCompare ((CFStringRef) mediaType, kUTTypeImage, 0)
        == kCFCompareEqualTo) {
        
        editedImage = (UIImage *) [info objectForKey:
                                   UIImagePickerControllerEditedImage];
        originalImage = (UIImage *) [info objectForKey:
                                     UIImagePickerControllerOriginalImage];
        
        if (editedImage) {
            imageToSave = editedImage;
        } else {
            imageToSave = originalImage;
        }
        
        self.mediaContent = [[[NSData alloc] initWithData: UIImageJPEGRepresentation(imageToSave,0.2)] autorelease];
        self.mediaType = 2;
        
        // NSData *imageToUpload = [[[NSData alloc] initWithData: UIImageJPEGRepresentation(imageToSave,0.7)] autorelease];
        
        
        // fileNameUploaded = [OpsService uploadMedia:imageToUpload];
        
        // Processa o Upload
        self.imegePreview.image = [[[UIImage alloc] initWithData: self.mediaContent] autorelease];
        
        // Save the new image (original or edited) to the Camera Roll
        // UIImageWriteToSavedPhotosAlbum (imageToSave, nil, nil,nil);
        // UIImageWriteToSavedPhotosAlbum (imageToSave, self, @selector(image:didFinishSavingWithError:contextInfo:) , nil);
    }
    
    // Handle a movie capture
    if (CFStringCompare ((CFStringRef) mediaType, kUTTypeMovie, 0)
        == kCFCompareEqualTo) {
        
        NSString *moviePath = [[info objectForKey:
                                UIImagePickerControllerMediaURL] path];
        
        if (UIVideoAtPathIsCompatibleWithSavedPhotosAlbum (moviePath)) {
            // UISaveVideoAtPathToSavedPhotosAlbum (moviePath,nil,nil,nil);
            // UISaveVideoAtPathToSavedPhotosAlbum (moviePath, self, @selector(image:didFinishSavingWithError:contextInfo:), nil);
        }
    }
    
    [self dismissModalViewControllerAnimated:YES];

}



- (void) imagePickerControllerDidCancel:(UIImagePickerController *)picker 
{
    [self dismissModalViewControllerAnimated:YES];
    // [picker release];
}

/*

- (void)image:(UIImage *)image didFinishSavingWithError:(NSError *)error contextInfo:(void *)contextInfo
{
    UIAlertView *alert;
    
    // Unable to save the image  
    if (error)
        alert = [[UIAlertView alloc] initWithTitle:@"Erro" 
                                           message:@"Não foi possível gravar a imagem!" 
                                          delegate:self cancelButtonTitle:@"Ok" 
                                 otherButtonTitles:nil];
    else // All is well
        alert = [[UIAlertView alloc] initWithTitle:@"Sucesso" 
                                           message:@"Imagem gravada com sucesso!" 
                                          delegate:self cancelButtonTitle:@"Ok" 
                                 otherButtonTitles:nil];
    [alert show];
    [alert release];
}


*/


-(IBAction)recordMessage {
    
    NSString *strReset = [OpsService resetServices];
    
    [self showMessage:@"Recurso de gravação de voz ainda será implementado!" title:strReset];    
}

-(IBAction)keyboardDisapear
{
    [textViewMessage resignFirstResponder];
}

#pragma mark - View lifecycle

-(void) viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
 
    NSMutableString *strMessage = [[NSMutableString alloc] initWithCapacity:140];
    [strMessage appendFormat:@"Sua mensagem para %@: ",serviceName];
    labelMessage.text = strMessage;

 
     [strMessage release];
    
}


- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    
    self.mediaContent = nil;
    self.mediaType = 0;
    
    UIImage *navBarImage = [UIImage imageNamed:@"background.png"];
    self.view.backgroundColor = [UIColor colorWithPatternImage:navBarImage];
 
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
        
    serviceName = nil;
    serviceId = nil;
    textViewMessage = nil;
    imegePreview = nil;
    mediaContent = nil;
    fileNameUploaded = nil;
    
    
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}


-(IBAction)hideKeyboard:(id)sender
{
    [textViewMessage resignFirstResponder];
}

- (void) dealloc
{
    [mediaContent release];
    [fileNameUploaded release];
    [serviceId release];
    [serviceName release];
    [textViewMessage release];
    [imegePreview release];
    [super dealloc];
}

@end
