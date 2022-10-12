package com.gmail.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gmail.exception.NoMailFound;
import com.gmail.module.Mail;
import com.gmail.module.User;
import com.gmail.repository.MailDao;
import com.gmail.util.GetCurrentUser;

@Service
public class MailServiceImpl implements MailService {

	@Autowired
	private GetCurrentUser getCurrentUser;

	@Autowired
	private MailDao mailDao;

	@Override
	public List<Mail> inbox() {

		User user = getCurrentUser.getCurrentUser();

		if (user == null) {
			throw new UsernameNotFoundException("User session expired, Please Login Again");
		} else {
			return mailDao.findByRecievers(user);
		}
	}

	@Override
	public List<Mail> sentBox() {

		User user = getCurrentUser.getCurrentUser();

		if (user == null) {
			throw new UsernameNotFoundException("User session expired, Please Login Again");
		} else {
			List<Mail> draftedMail = user.getDraft();

			List<Mail> mailSentByUser = user.getSent();

			for (Mail mailDrafted : draftedMail) {
				mailSentByUser.remove(mailDrafted);
			}
			mailSentByUser.removeAll(user.getTrashMails());

			return mailSentByUser;

		}
	}

	@Override
	public List<Mail> draftedMail() {

		User user = getCurrentUser.getCurrentUser();

		if (user == null) {
			throw new UsernameNotFoundException("User session expired, Please Login Again");
		} else {

			return user.getDraft();

		}
	}

	@Override
	public List<Mail> getAllMail() {

		User user = getCurrentUser.getCurrentUser();

		if (user == null) {
			throw new UsernameNotFoundException("User session expired, Please Login Again");
		} else {
			Set<Mail> mailSet = new HashSet<>();

			if (user.getDraft().size() > 0)
				mailSet.addAll(user.getDraft());
			if (user.getSent().size() > 0)
				mailSet.addAll(user.getSent());
			if (user.getStarred().size() > 0)
				mailSet.addAll(user.getStarred());

			mailSet.addAll(inbox());

			mailSet.removeAll(user.getTrashMails());

			List<Mail> allMail = new ArrayList<>(mailSet);

			return allMail;

		}

	}

	@Override
	public List<Mail> searchMail(String keyword) {

		List<Mail> mailList = getAllMail();

		Set<Mail> resultMails = new HashSet<>();

		for (Mail res : mailList) {
			if (res.getBody().toLowerCase().contains(keyword.toLowerCase())
					|| res.getSender().getEmail().toLowerCase().contains(keyword.toLowerCase())) {
				resultMails.add(res);
			}
		}

		for (Mail res : mailList) {
			List<User> reciever = res.getRecievers();
			for (User user : reciever) {
				if (user.getEmail().toLowerCase().contains(keyword.toLowerCase())) {
					resultMails.add(res);
				}
			}

		}

		List<Mail> allMail = new ArrayList<>(resultMails);

		return allMail;

	}

	@Override
	public List<Mail> getDeletedMails() {

		User currentLogedInUser = getCurrentUser.getCurrentUser();

		if (currentLogedInUser == null) {
			throw new UsernameNotFoundException("User session expired, Please Login Again");
		} else {
			List<Mail> deletedMails = currentLogedInUser.getTrashMails();

			return deletedMails;

		}
	}

	@Override
	public List<Mail> getStarredMails() {

		User currentLogedInUser = getCurrentUser.getCurrentUser();
		if (currentLogedInUser == null) {
			throw new UsernameNotFoundException("User session expired, Please Login Again");
		} else {

			List<Mail> starredMails = currentLogedInUser.getStarred();
			try {
				starredMails.removeAll(getDeletedMails());
			} catch (NoMailFound n) {

			}
			return starredMails;

		}
	}

}
