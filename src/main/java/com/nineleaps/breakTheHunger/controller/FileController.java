package com.nineleaps.breakTheHunger.controller;

import com.nineleaps.breakTheHunger.dto.ItemRequestDto;
import com.nineleaps.breakTheHunger.elasticsearch.ElasticSearchOperation;
import com.nineleaps.breakTheHunger.entities.ImageEntity;
import com.nineleaps.breakTheHunger.entities.ItemEntity;
import com.nineleaps.breakTheHunger.repositories.ImageRepository;
import com.nineleaps.breakTheHunger.service.FileStorageService;
import com.nineleaps.breakTheHunger.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.core.io.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping("breakTheHunger")
public class FileController {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    ItemService itemService;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    ElasticSearchOperation elasticSearchOperation;

    @RequestMapping( value = "/supplierRegistration", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResponseEntity uploadFile(@RequestParam("userId") String userId,
                                     @RequestParam("itemName") String itemName,
                                     @RequestParam("type") String type,
                                     @RequestParam("description") String description,
                                     @RequestParam("price") int price,
                                     @RequestParam("file") MultipartFile file) {

        if(description == null || userId == null
                || itemName == null || type == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Attributes are mandatory cannot be Null");
        }

        ItemRequestDto itemRequestDto = new ItemRequestDto();

        itemRequestDto.setUserId(userId);
        itemRequestDto.setItemName(itemName);
        itemRequestDto.setType(type);
        itemRequestDto.setDescription(description);
        itemRequestDto.setPrice(price);
        ItemEntity itemEntity = itemService.saveItemDetails(itemRequestDto);
        String fileName = fileStorageService.storeFile(file);
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setImageId(itemEntity.getItemId()+":"+fileName);
        imageEntity.setItemId(itemEntity.getItemId());
        imageEntity.setFileName(fileName);

        imageRepository.save(imageEntity);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile")
                .path(fileName)
                .toUriString();
        return ResponseEntity.status(HttpStatus.OK).body("{\"message\":" + "\""
                + "Item details has been Successfully Registered" + "\"}");

//        return new UploadFileResponse(fileName, fileDownloadUri,
//                file.getContentType(), file.getSize());
    }

    @GetMapping("/downloadFile")
    public ResponseEntity<Resource> downloadFile(@RequestParam String itemId, HttpServletRequest request) {
        // Load file as Resource

        ImageEntity imageEntity = elasticSearchOperation.fetchElasticImageEntity(itemId);

        Resource resource = fileStorageService.loadFileAsResource(imageEntity.getFileName());

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            System.out.println("Error fetching the image");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
