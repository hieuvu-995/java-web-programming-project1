package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Note {
    private int noteid;
    private String notetitle;
    private String notedescription;
    private int userid;
}