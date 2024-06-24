package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postAccountHandler);
        app.post("/login", this::postLoginHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getOneMessagesByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.post("/messages", this::postMessageHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);

        app.get("/accounts/{account_id}/messages", this::getAllMessageFromUserHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void postAccountHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account addAcount = accountService.addAccount(account);
        if(addAcount != null){
            context.json(mapper.writeValueAsString(addAcount));
        }else{
            context.status(400);
        }

    }

    private void postLoginHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account addAcount = accountService.login(account);
        if(addAcount != null){
            context.json(mapper.writeValueAsString(addAcount));
        }else{
            context.status(401);
        }

    }

    private void postMessageHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message msg = mapper.readValue(context.body(), Message.class);
        Message addMessage = messageService.createMessage(msg);
        if(addMessage != null){
            context.json(mapper.writeValueAsString(addMessage));
        }else{
            context.status(400);
        }

    }

    private void getOneMessagesByIdHandler(Context context) throws JsonProcessingException{
        int id = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.getOneMessagesById(id);
        if(message == null ){
            context.status(200);
        }else{
            context.json(message);
        }
    }

    private void getAllMessagesHandler(Context context) throws JsonProcessingException{
        List<Message> messages = messageService.getAllMessages();
        context.json(messages);
    }

    private void deleteMessageHandler(Context context) throws JsonProcessingException{
        int msg_id = Integer.parseInt(context.pathParam("message_id"));
        Message deleteMessage = messageService.deleteMessage(msg_id);
        if(deleteMessage == null ){
            context.status(200);
        }else{
            context.json(deleteMessage);
        }
    }

    private void updateMessageHandler(Context context) throws JsonProcessingException{
        int msg_id = Integer.parseInt(context.pathParam("message_id"));
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message updatedMessage = messageService.updatMessage(message.getMessage_text(), msg_id);
        if(updatedMessage != null){
            context.json(updatedMessage);
        }else{
            context.status(400);
        }
    }

    private void getAllMessageFromUserHandler(Context context) throws JsonProcessingException{
        int acc_id = Integer.parseInt(context.pathParam("account_id"));
        List<Message> messages = messageService.getAllMessagesByAccId(acc_id);

        if(messages != null){
            context.json(messages);
        }else{
            context.status(200);
        }

    }

}