package com.itwillbs.component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileComponent {

	private static final Logger logger = LoggerFactory.getLogger(FileComponent.class);
	
	private String saveDirectory = "/usr/local/tomcat/upload/";
	
	public FileComponent() {
		File dir = new File(saveDirectory);
		if(dir.exists() == false) {
			dir.mkdirs();
		}
	}
	
	// 파일 업로드 메소드
	public List<String> uploadFiles(MultipartFile[] files) {
        List<String> storedFileNames = new ArrayList<>();

        if (files == null || files.length == 0) {
            return storedFileNames;
        }

        for (MultipartFile file : files) {
            if (file.isEmpty()) continue;

            String extName = file.getOriginalFilename()
                    .substring(file.getOriginalFilename().lastIndexOf("."));
            String storedFileName = UUID.randomUUID().toString().replace("-", "") + extName;
            File dest = new File(saveDirectory, storedFileName);

            try {
                file.transferTo(dest);
                storedFileNames.add(storedFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return storedFileNames;
    }

    // 단일 파일 삭제
    public void delete(String storedFileName) {
        File file = new File(saveDirectory, storedFileName);
        if (file.exists()) {
            file.delete();
            System.out.println("삭제 완료: " + storedFileName);
        }
    }
    
    /////////////////////////////////////////
    // 단일 파일 업로드
    public String upload(MultipartFile f) {
    	String extName = f.getOriginalFilename().substring(f.getOriginalFilename().lastIndexOf("."));
    	String storedFileName = UUID.randomUUID().toString().replace("-", "");
    	storedFileName += extName;
    	File dest = new File(saveDirectory, storedFileName);
    	try {
    		f.transferTo(dest);
    		return storedFileName;
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
    	return null;
    }
    
}
