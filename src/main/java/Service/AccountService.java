package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    AccountDAO accountDAO;

    public AccountService(){
        this.accountDAO = new AccountDAO();
    }

    public Account addAccount(Account acc) {

        if(accountDAO.getAccountById(acc.getAccount_id()) !=null){
            return null;
        };

        if(acc.getPassword().length() < 4){
            return null;
        }

        if(acc.getUsername().isEmpty()){
            return null;
        }

        return accountDAO.insertAccount(acc);
    }

    public Account getAccountById(int acc_id){
        
        return accountDAO.getAccountById(acc_id);
    }

    public Account login(Account acc){

        Account account = accountDAO.getAccountByUsernameAndPassword(acc.getUsername(), acc.getPassword());

        if(account != null){
            return account;
        }
        
        return null;
    }

}
