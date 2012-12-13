package library;

import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author ITExplore
 */
public class EnterpriseBean {
    
    public static Object lookupBean(String beanName) throws NamingException {
        InitialContext ic = new InitialContext();
        
        return ic.lookup("java:comp/env/" + beanName);
    }
    
}
