// package com.google.sps.utils;

// import com.google.cloud.vision.v1.AnnotateImageRequest;
// import com.google.cloud.vision.v1.AnnotateImageResponse;
// import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
// import com.google.cloud.vision.v1.Feature;
// import com.google.cloud.vision.v1.Feature.Type;
// import com.google.cloud.vision.v1.Image;
// import com.google.cloud.vision.v1.ImageAnnotatorClient;
// import com.google.cloud.vision.v1.ImageSource;
// import com.google.cloud.vision.v1.SafeSearchAnnotation;
// import com.google.cloud.vision.v1.Likelihood;
// import java.io.IOException;
// import java.util.ArrayList;
// import java.util.List;

// public class SafeSearchUtils {

//   // Detects whether the specified image has features you would want to
//   // moderate.
//   public static boolean detectSafeSearch(String gcsPath) {
    
//     List<AnnotateImageRequest> requests = new ArrayList<>();

//     ImageSource imgSource = ImageSource.newBuilder().setGcsImageUri(gcsPath).build();
//     Image img = Image.newBuilder().setSource(imgSource).build();
//     Feature feat = Feature.newBuilder().setType(Type.SAFE_SEARCH_DETECTION).build();
//     AnnotateImageRequest request =
//         AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
//     requests.add(request);
    
//     // Initialize client that will be used to send requests. This client only needs to be created
//     // once, and can be reused for multiple requests. After completing all of your requests, call
//     // the "close" method on the client to safely clean up any remaining background resources.
//     try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
        
//       BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
//       List<AnnotateImageResponse> responses = response.getResponsesList();
//       client.close();

//       for (AnnotateImageResponse res : responses) {
        
//         if (res.hasError()) {
//           //System.out.println("Error: %s%n"+ res.getError().getMessage());
//           return false;
//         }

//         SafeSearchAnnotation annotation = res.getSafeSearchAnnotation();
//         Likelihood LIKELY = LIKELY;
//         Likelihood VERY_LIKELY = VERY_LIKELY;
//         if(annotation.getAdult() == LIKELY || annotation.getAdult() == VERY_LIKELY){
//             return false;
//         }
//         if(annotation.getMedical() == LIKELY || annotation.getMedical() == VERY_LIKELY){
//             return false;
//         }
//         if(annotation.getSpoof() == LIKELY || annotation.getSpoof() == VERY_LIKELY){
//             return false;
//         }
//         if(annotation.getViolence() == LIKELY || annotation.getViolence() == VERY_LIKELY){
//             return false;
//         }
//         if(annotation.getRacy() == LIKELY || annotation.getRacy() == VERY_LIKELY){
//             return false;
//         }
//       }
//       return true;
//     }catch(IOException e){
//         return false;
//     }
//   }
// }