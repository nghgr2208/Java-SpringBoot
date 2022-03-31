package com.springboot.elasticsearch.service;

import com.springboot.elasticsearch.document.Export;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class ExportData {
    public final ExportService service;

    @Autowired
    public ExportData(final ExportService service) {
        this.service = service;
    }

    private static Export buildExport( String id, String post,String comment){
        Export export = new Export();
        export.setId(id);
        export.setPost(post);
        export.setComment(comment);

        return export;
    }

    public void insertData(){
        service.index(buildExport("1","Đại học tại Hà Nội","Đại học Bách khoa Hà Nội"));
        service.index(buildExport("2","Đại học tại Hà Nội","Đại học Công nghệ – Đại học Quốc gia Hà Nội"));
        service.index(buildExport("3","Đại học tại Hà Nội","Đại học Công đoàn"));
        service.index(buildExport("4","Đại học tại Hà Nội","Đại học Công nghệ Giao thông Vận tải"));
        service.index(buildExport("5","Đại học tại Hà Nội","Đại học Công nghiệp Hà Nội"));
        service.index(buildExport("6","Cao đẳng tại Hà Nội ","Cao đẳng Kinh tế Công nghiệp Hà Nội"));
        service.index(buildExport("7","Cao đẳng tại Hà Nội","Cao đẳng Kinh tế Kỹ thuật thương mại"));
        service.index(buildExport("8","Cao đẳng tại Hà Nội","Cao đẳng Kinh tế Kỹ thuật Trung ương"));
        service.index(buildExport("9","Cao đẳng tại Hà Nội","Cao đẳng Kỹ thuật Công nghệ Bách khoa"));
        service.index(buildExport("10","Cao đẳng tại Hà Nội","Cao đẳng FPT"));
    }
}
