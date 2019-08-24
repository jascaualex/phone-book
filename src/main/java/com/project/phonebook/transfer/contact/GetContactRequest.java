package com.project.phonebook.transfer.contact;

public class GetContactRequest {

    private String partialName;

    public String getPartialName() {
        return partialName;
    }

    public void setPartialName(String partialName) {
        this.partialName = partialName;
    }

    @Override
    public String toString() {
        return "GetContactRequest{" +
                "partialName='" + partialName + '\'' +
                '}';
    }
}
