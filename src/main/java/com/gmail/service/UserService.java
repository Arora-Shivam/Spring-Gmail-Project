package com.gmail.service;

import com.gmail.exception.UserAlreadyExistException;
import com.gmail.module.Mail;
import com.gmail.module.MailDto;
import com.gmail.module.User;

public interface UserService {

	public boolean addUser(User user) throws UserAlreadyExistException;

	public boolean deleteUser();

	public boolean sentMail(MailDto mailDto);

	public boolean starredMail(int mailId);

	public boolean draftMail(MailDto mail);

	public boolean deleteMail(int mailId);

	public boolean restoreMail(Mail mail);

	public boolean isValidPassword(String password);

	public void emptyTrash();
}
