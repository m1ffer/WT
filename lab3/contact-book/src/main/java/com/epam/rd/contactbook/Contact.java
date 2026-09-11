package com.epam.rd.contactbook;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class Contact {
    private static final int NUMBERS_LIMIT = 1;
    private static final int EMAILS_LIMIT = 3;
    private static final int LINKS_LIMIT = 5;

    private final ContactInfo[] numbers = new ContactInfo[NUMBERS_LIMIT],
            emails = new ContactInfo[EMAILS_LIMIT],
            links = new ContactInfo[LINKS_LIMIT];

    private int numbersI, emailsI, linksI;

    private String contactName;

    public Contact(String contactName) {
        this.contactName = contactName;
    }

    private static boolean nullOrBlank(String s){
        return s == null || s.isBlank();
    }

    public void rename(String newName) {
        if (!nullOrBlank(newName))
            contactName = newName;
    }

    private class NameContactInfo implements ContactInfo{
        @Override
        public String getTitle(){
            return "Name";
        }
        @Override
        public String getValue() {
            return contactName;
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class Email implements ContactInfo{
        private final String title = "Email";
        private final String value;
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class Social implements ContactInfo{
        private final String title;
        private final String value;
    }

    public Email addEmail(String localPart, String domain) {
        if (emailsI == EMAILS_LIMIT ||
            nullOrBlank(localPart) ||
            nullOrBlank(domain))
            return null;
        Email res = new Email(localPart + "@" + domain);
        emails[emailsI++] = res;
        return res;
    }


    public Email addEpamEmail(String firstname, String lastname) {
        if (emailsI == EMAILS_LIMIT ||
                nullOrBlank(firstname) ||
                nullOrBlank(lastname))
            return null;
        Email res = new Email(firstname + "_" + lastname + "@epam.com"){
            @Override
            public String getTitle(){
                return "Epam Email";
            }
        };
        emails[emailsI++] = res;
        return res;
    }

    public ContactInfo addPhoneNumber(int code, String number) {
        if (numbersI == NUMBERS_LIMIT ||
            nullOrBlank(number))
            return null;
        ContactInfo res = new ContactInfo() {
            @Override
            public String getTitle() {
                return "Tel";
            }
            @Override
            public String getValue() {
                return "+" + code + " " + number;
            }
        };
        numbers[numbersI++] = res;
        return res;
    }

    public Social addTwitter(String twitterId) {
        return addSocialMedia("Twitter", twitterId);
    }

    public Social addInstagram(String instagramId) {
        return addSocialMedia("Instagram", instagramId);
    }

    public Social addSocialMedia(String title, String id) {
        if (linksI == LINKS_LIMIT ||
                nullOrBlank(id) ||
                nullOrBlank(title))
            return null;
        Social res = new Social(title, id);
        links[linksI++] = res;
        return res;
    }

    public ContactInfo[] getInfo() {
        ContactInfo[] res = new ContactInfo[1 + numbersI +
                emailsI + linksI];
        res[0] = new NameContactInfo();
        System.arraycopy(numbers, 0, res, 1, numbersI);
        System.arraycopy(emails, 0, res, 1 + numbersI, emailsI);
        System.arraycopy(links, 0, res, 1 + numbersI + emailsI, linksI);
        return res;
    }

}
