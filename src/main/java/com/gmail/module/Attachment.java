package com.gmail.module;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class Attachment {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private
    Integer id;
    private String FileName;
    private String FileType;
    @Lob
    private byte[] data;

    public Attachment ( String FileName , String FileType , byte[] data ) {
        this.FileName = FileName;
        this.FileType = FileType;
        this.data = data;
    }
//	@Embedded
//	private Video video;
//	
//	@Embedded
//	private Audio audio;
//	
//	@Embedded
//	private PDF pdf;
}
