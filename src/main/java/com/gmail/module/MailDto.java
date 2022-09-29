package com.gmail.module;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MailDto {

    private List<User> recievers;

    private String body;


}
