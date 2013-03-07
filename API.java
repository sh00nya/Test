package ForceAPI;

import java.rmi.RemoteException;
import java.io.*;

import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPException;

import com.sforce.soap.enterprise.DeleteResult;
import com.sforce.soap.enterprise.LoginResult;
import com.sforce.soap.enterprise.QueryResult;
import com.sforce.soap.enterprise.SaveResult;
import com.sforce.soap.enterprise.SessionHeader;
import com.sforce.soap.enterprise.SforceServiceLocator;
import com.sforce.soap.enterprise.SoapBindingStub;
import com.sforce.soap.enterprise.fault.LoginFault;
import com.sforce.soap.enterprise.fault.UnexpectedErrorFault;
import com.sforce.soap.enterprise.sobject.Account;
import com.sforce.soap.enterprise.sobject.SObject;
import com.sforce.soap.enterprise.*;
import com.sforce.soap.enterprise.DescribeGlobalSObjectResult;;

public class APICLASS {

  private SoapBindingStub binding = null;
    private LoginResult lr = null; // maintain the login results
    
    private void doLogin()
    {
    	String userName="";
    	String passwd="";
    	if(userName.length()==0 || passwd.length()==0)
    		return;
    	try
    	{
    		binding =(SoapBindingStub) new SforceServiceLocator().getSoap();
    		
    	}
    	catch(ServiceException ex1)
    	{
    		System.out.println("service Exceptio==>"+ex1.getMessage());
    		return ;
    	}
    	
    	
    	try
    	{
    		lr=binding.login(userName, passwd);
    		
    	}
    	catch(UnexpectedErrorFault ex2)
    	{
    		System.out.println("Unexpected error"+ex2.getExceptionMessage());
    		return ;
    	}
    	catch(LoginFault ex3)
    	{
    		System.out.println("Loginfault"+ex3.getExceptionMessage());
    		return ;
    	}
    	catch(RemoteException ex4)
    	{
    		System.out.println("Remote Exception"+ex4.getMessage());
    		return ;
    	}
    	
    	
    	System.out.println("***************************************************************************************");
    	System.out.println("========================================================================================");
    	System.out.println("Logged In successfully");
    	System.out.println("User Name==>"+lr.getUserInfo().getUserName());
    	System.out.println("=========================================================================================");
    	binding._setProperty(SoapBindingStub.ENDPOINT_ADDRESS_PROPERTY, lr.getServerUrl());
    	SessionHeader sh= new SessionHeader();
    	sh.setSessionId(lr.getSessionId());
    	String sforceURI=new SforceServiceLocator().getServiceName().getNamespaceURI();
    	binding.setHeader(sforceURI,"SessionHeader",sh);
    	return ;
    	
    }
    public void sObjectField()
    {
    	try
    	{
	    	DescribeGlobalResult describeGlobalResult =binding.describeGlobal();
	    	DescribeGlobalSObjectResult[] sobjectResults = describeGlobalResult.getSobjects();
	    	int cnt=0;
	    	for (int i = 0; i < sobjectResults.length; i++)
	    	{

	    			String objName=sobjectResults[i].getName();
	    			DescribeSObjectResult describeSObjectResult =binding.describeSObject(objName);
	    			if (describeSObjectResult != null)
	    			{
	    				if (describeSObjectResult.isCreateable())
	    				{
	    					//System.out.println(describeSObjectResult.getName());
	    					if(!describeSObjectResult.getName().endsWith("__c") )
	    					{
	    						System.out.println(describeSObjectResult.getName());
	    						cnt++;
	    					}
	    				}
	    			}
	    	}
	    	System.out.println("Total Standard Object=="+cnt);
    	}
    	
    	catch(RemoteException re)
    	{
    		System.out.println(re.getMessage());
    	}
    	
    }
    
    public static void main(String args[])
    {
    	APICLASS ap=new APICLASS();
    	ap.doLogin();
    	ap.sObjectField();
    }
}
