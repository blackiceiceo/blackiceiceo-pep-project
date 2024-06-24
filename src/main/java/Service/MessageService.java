package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Account;
import Model.Message;

public class MessageService {
    MessageDAO messageDAO;
    AccountService accountService;

    public MessageService(){
        this.messageDAO = new MessageDAO();
        this.accountService = new AccountService();
    }

    public Message createMessage(Message msg){

        if(msg.getMessage_text().length() > 255 ){
            System.out.println("Message content is over 255 characters");
            return null;
        }

        if(msg.getMessage_text().isEmpty()) {
            System.out.println("Message content is empty");
            return null;
        }
        
        if(accountService.getAccountById(msg.getPosted_by()) == null){
            System.out.println("Account associated with message does not exist");
            return null;
        }

        return messageDAO.insertMessage(msg);
    }

    public Message getOneMessagesById(int message_id){
        return messageDAO.getOneMessagesById(message_id);
    }

    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    public Message updatMessage(String msg, int msg_id){
        Message message = messageDAO.getOneMessagesById(msg_id);

        if(msg.length() > 255 ){
            System.out.println("Message content is over 255 characters");
            return null;
        }

        if(msg.isEmpty()) {
            System.out.println("Message content is empty");
            return null;
        }

        if(messageDAO.getOneMessagesById(msg_id) == null){
            System.out.println("Message does not exist");
            return null;
        }

        return messageDAO.updateMessage(message, msg);
    }

    public List<Message> getAllMessagesByAccId(int acc_id){
        return messageDAO.getAllMessagesByAccId(acc_id);
    }

    public Message deleteMessage(int msg_id){

        Message msg = messageDAO.getOneMessagesById(msg_id);

        if(msg == null) {
            return null;
        }

        messageDAO.deleteMessage(msg);

        return msg;
    }

}
