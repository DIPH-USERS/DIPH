package com.tattvafoundation.diphonline.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tattvafoundation.diphonline.model.DeleteUserBean;
import com.tattvafoundation.diphonline.model.ManageCreateUserReturnStatusBean;
import com.tattvafoundation.diphonline.model.User_Info;
import com.tattvafoundation.diphonline.model.ViewExistingUsers;
import com.tattvafoundation.diphonline.model.ViewUsers_UserBean;
import com.tattvafoundation.diphonline.repository.ManageUserDAO;

@CrossOrigin(origins = "*")
@RestController
public class ManageUserController {

	@Autowired
	ManageUserDAO dao; 
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public void sendPassword2(String user, String upassword, String to) {

		String host = "smtp.gmail.com";
		final String username = "lshtm.diph@gmail.com";// change accordingly
		final String password = "desktop@123";// change accordingly

		// String to="lshtm.diph@gmail.com";//change accordingly

		try {

			String recipient = to;

			Properties props = new Properties();

			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.from", "myemail@gmail.com");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.port", "587");
			props.setProperty("mail.debug", "true");

			Session session = Session.getInstance(props, null);
			MimeMessage msg = new MimeMessage(session);

			msg.setRecipients(Message.RecipientType.TO, recipient);
			msg.setSubject("DIPH Application Forgot Password Request");
			msg.setSentDate(new Date());
			msg.setText("Hello, world!\n");
			msg.setContent(emailsContents("" + user, upassword, "" + user), "text/html");

			Transport transport = session.getTransport("smtp");

			transport.connect(username, password);
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	
	public static String emailsContents(String user_id, String password, String Name) {
		String str = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n"
				+ "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" + "  <head>\n"
				+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n"
				+ "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n"
				+ "    <title>forgot Password</title>\n" + "    <!-- \n"
				+ "    The style block is collapsed on page load to save you some scrolling.\n"
				+ "    Postmark automatically inlines all CSS properties for maximum email client \n"
				+ "    compatibility. You can just update styles here, and Postmark does the rest.\n" + "    -->\n"
				+ "    <style type=\"text/css\" rel=\"stylesheet\" media=\"all\">\n"
				+ "    /* Base ------------------------------ */\n" + "    \n" + "    *:not(br):not(tr):not(html) {\n"
				+ "      font-family: Arial, 'Helvetica Neue', Helvetica, sans-serif;\n"
				+ "      box-sizing: border-box;\n" + "    }\n" + "    \n" + "    body {\n"
				+ "      width: 100% !important;\n" + "      height: 100%;\n" + "      margin: 0;\n"
				+ "      line-height: 1.4;\n" + "      background-color: #F2F4F6;\n" + "      color: #74787E;\n"
				+ "      -webkit-text-size-adjust: none;\n" + "    }\n" + "    \n" + "    p,\n" + "    ul,\n"
				+ "    ol,\n" + "    blockquote {\n" + "      line-height: 1.4;\n" + "      text-align: left;\n"
				+ "    }\n" + "    \n" + "    a {\n" + "      color: #3869D4;\n" + "    }\n" + "    \n"
				+ "    a img {\n" + "      border: none;\n" + "    }\n" + "    \n" + "    td {\n"
				+ "      word-break: break-word;\n" + "    }\n" + "    /* Layout ------------------------------ */\n"
				+ "    \n" + "    .email-wrapper {\n" + "      width: 100%;\n" + "      margin: 0;\n"
				+ "      padding: 0;\n" + "      -premailer-width: 100%;\n" + "      -premailer-cellpadding: 0;\n"
				+ "      -premailer-cellspacing: 0;\n" + "      background-color: #F2F4F6;\n" + "    }\n" + "    \n"
				+ "    .email-content {\n" + "      width: 100%;\n" + "      margin: 0;\n" + "      padding: 0;\n"
				+ "      -premailer-width: 100%;\n" + "      -premailer-cellpadding: 0;\n"
				+ "      -premailer-cellspacing: 0;\n" + "    }\n" + "    /* Masthead ----------------------- */\n"
				+ "    \n" + "    .email-masthead {\n" + "      padding: 25px 0;\n" + "      text-align: center;\n"
				+ "    }\n" + "    \n" + "    .email-masthead_logo {\n" + "      width: 94px;\n" + "    }\n" + "    \n"
				+ "    .email-masthead_name {\n" + "      font-size: 16px;\n" + "      font-weight: bold;\n"
				+ "      color: #bbbfc3;\n" + "      text-decoration: none;\n" + "      text-shadow: 0 1px 0 white;\n"
				+ "    }\n" + "    /* Body ------------------------------ */\n" + "    \n" + "    .email-body {\n"
				+ "      width: 100%;\n" + "      margin: 0;\n" + "      padding: 0;\n"
				+ "      -premailer-width: 100%;\n" + "      -premailer-cellpadding: 0;\n"
				+ "      -premailer-cellspacing: 0;\n" + "      border-top: 1px solid #EDEFF2;\n"
				+ "      border-bottom: 1px solid #EDEFF2;\n" + "      background-color: #FFFFFF;\n" + "    }\n"
				+ "    \n" + "    .email-body_inner {\n" + "      width: 570px;\n" + "      margin: 0 auto;\n"
				+ "      padding: 0;\n" + "      -premailer-width: 570px;\n" + "      -premailer-cellpadding: 0;\n"
				+ "      -premailer-cellspacing: 0;\n" + "      background-color: #FFFFFF;\n" + "    }\n" + "    \n"
				+ "    .email-footer {\n" + "      width: 570px;\n" + "      margin: 0 auto;\n" + "      padding: 0;\n"
				+ "      -premailer-width: 570px;\n" + "      -premailer-cellpadding: 0;\n"
				+ "      -premailer-cellspacing: 0;\n" + "      text-align: center;\n" + "    }\n" + "    \n"
				+ "    .email-footer p {\n" + "      color: #AEAEAE;\n" + "    }\n" + "    \n" + "    .body-action {\n"
				+ "      width: 100%;\n" + "      margin: 30px auto;\n" + "      padding: 0;\n"
				+ "      -premailer-width: 100%;\n" + "      -premailer-cellpadding: 0;\n"
				+ "      -premailer-cellspacing: 0;\n" + "      text-align: center;\n" + "    }\n" + "    \n"
				+ "    .body-sub {\n" + "      margin-top: 25px;\n" + "      padding-top: 25px;\n"
				+ "      border-top: 1px solid #EDEFF2;\n" + "    }\n" + "    \n" + "    .content-cell {\n"
				+ "      padding: 35px;\n" + "    }\n" + "    \n" + "    .preheader {\n"
				+ "      display: none !important;\n" + "      visibility: hidden;\n" + "      mso-hide: all;\n"
				+ "      font-size: 1px;\n" + "      line-height: 1px;\n" + "      max-height: 0;\n"
				+ "      max-width: 0;\n" + "      opacity: 0;\n" + "      overflow: hidden;\n" + "    }\n"
				+ "    /* Attribute list ------------------------------ */\n" + "    \n" + "    .attributes {\n"
				+ "      margin: 0 0 21px;\n" + "    }\n" + "    \n" + "    .attributes_content {\n"
				+ "      background-color: #EDEFF2;\n" + "      padding: 16px;\n" + "    }\n" + "    \n"
				+ "    .attributes_item {\n" + "      padding: 0;\n" + "    }\n"
				+ "    /* Related Items ------------------------------ */\n" + "    \n" + "    .related {\n"
				+ "      width: 100%;\n" + "      margin: 0;\n" + "      padding: 25px 0 0 0;\n"
				+ "      -premailer-width: 100%;\n" + "      -premailer-cellpadding: 0;\n"
				+ "      -premailer-cellspacing: 0;\n" + "    }\n" + "    \n" + "    .related_item {\n"
				+ "      padding: 10px 0;\n" + "      color: #74787E;\n" + "      font-size: 15px;\n"
				+ "      line-height: 18px;\n" + "    }\n" + "    \n" + "    .related_item-title {\n"
				+ "      display: block;\n" + "      margin: .5em 0 0;\n" + "    }\n" + "    \n"
				+ "    .related_item-thumb {\n" + "      display: block;\n" + "      padding-bottom: 10px;\n"
				+ "    }\n" + "    \n" + "    .related_heading {\n" + "      border-top: 1px solid #EDEFF2;\n"
				+ "      text-align: center;\n" + "      padding: 25px 0 10px;\n" + "    }\n"
				+ "    /* Discount Code ------------------------------ */\n" + "    \n" + "    .discount {\n"
				+ "      width: 100%;\n" + "      margin: 0;\n" + "      padding: 24px;\n"
				+ "      -premailer-width: 100%;\n" + "      -premailer-cellpadding: 0;\n"
				+ "      -premailer-cellspacing: 0;\n" + "      background-color: #EDEFF2;\n"
				+ "      border: 2px dashed #9BA2AB;\n" + "    }\n" + "    \n" + "    .discount_heading {\n"
				+ "      text-align: center;\n" + "    }\n" + "    \n" + "    .discount_body {\n"
				+ "      text-align: center;\n" + "      font-size: 15px;\n" + "    }\n"
				+ "    /* Social Icons ------------------------------ */\n" + "    \n" + "    .social {\n"
				+ "      width: auto;\n" + "    }\n" + "    \n" + "    .social td {\n" + "      padding: 0;\n"
				+ "      width: auto;\n" + "    }\n" + "    \n" + "    .social_icon {\n" + "      height: 20px;\n"
				+ "      margin: 0 8px 10px 8px;\n" + "      padding: 0;\n" + "    }\n"
				+ "    /* Data table ------------------------------ */\n" + "    \n" + "    .purchase {\n"
				+ "      width: 100%;\n" + "      margin: 0;\n" + "      padding: 35px 0;\n"
				+ "      -premailer-width: 100%;\n" + "      -premailer-cellpadding: 0;\n"
				+ "      -premailer-cellspacing: 0;\n" + "    }\n" + "    \n" + "    .purchase_content {\n"
				+ "      width: 100%;\n" + "      margin: 0;\n" + "      padding: 25px 0 0 0;\n"
				+ "      -premailer-width: 100%;\n" + "      -premailer-cellpadding: 0;\n"
				+ "      -premailer-cellspacing: 0;\n" + "    }\n" + "    \n" + "    .purchase_item {\n"
				+ "      padding: 10px 0;\n" + "      color: #74787E;\n" + "      font-size: 15px;\n"
				+ "      line-height: 18px;\n" + "    }\n" + "    \n" + "    .purchase_heading {\n"
				+ "      padding-bottom: 8px;\n" + "      border-bottom: 1px solid #EDEFF2;\n" + "    }\n" + "    \n"
				+ "    .purchase_heading p {\n" + "      margin: 0;\n" + "      color: #9BA2AB;\n"
				+ "      font-size: 12px;\n" + "    }\n" + "    \n" + "    .purchase_footer {\n"
				+ "      padding-top: 15px;\n" + "      border-top: 1px solid #EDEFF2;\n" + "    }\n" + "    \n"
				+ "    .purchase_total {\n" + "      margin: 0;\n" + "      text-align: right;\n"
				+ "      font-weight: bold;\n" + "      color: #2F3133;\n" + "    }\n" + "    \n"
				+ "    .purchase_total--label {\n" + "      padding: 0 15px 0 0;\n" + "    }\n"
				+ "    /* Utilities ------------------------------ */\n" + "    \n" + "    .align-right {\n"
				+ "      text-align: right;\n" + "    }\n" + "    \n" + "    .align-left {\n"
				+ "      text-align: left;\n" + "    }\n" + "    \n" + "    .align-center {\n"
				+ "      text-align: center;\n" + "    }\n" + "    /*Media Queries ------------------------------ */\n"
				+ "    \n" + "    @media only screen and (max-width: 600px) {\n" + "      .email-body_inner,\n"
				+ "      .email-footer {\n" + "        width: 100% !important;\n" + "      }\n" + "    }\n" + "    \n"
				+ "    @media only screen and (max-width: 500px) {\n" + "      .button {\n"
				+ "        width: 100% !important;\n" + "      }\n" + "    }\n"
				+ "    /* Buttons ------------------------------ */\n" + "    \n" + "    .button {\n"
				+ "      background-color: #3869D4;\n" + "      border-top: 10px solid #3869D4;\n"
				+ "      border-right: 18px solid #3869D4;\n" + "      border-bottom: 10px solid #3869D4;\n"
				+ "      border-left: 18px solid #3869D4;\n" + "      display: inline-block;\n" + "      color: #FFF;\n"
				+ "      text-decoration: none;\n" + "      border-radius: 3px;\n"
				+ "      box-shadow: 0 2px 3px rgba(0, 0, 0, 0.16);\n" + "      -webkit-text-size-adjust: none;\n"
				+ "    }\n" + "    \n" + "    .button--green {\n" + "      background-color: #22BC66;\n"
				+ "      border-top: 10px solid #22BC66;\n" + "      border-right: 18px solid #22BC66;\n"
				+ "      border-bottom: 10px solid #22BC66;\n" + "      border-left: 18px solid #22BC66;\n" + "    }\n"
				+ "    \n" + "    .button--red {\n" + "      background-color: #FF6136;\n"
				+ "      border-top: 10px solid #FF6136;\n" + "      border-right: 18px solid #FF6136;\n"
				+ "      border-bottom: 10px solid #FF6136;\n" + "      border-left: 18px solid #FF6136;\n" + "    }\n"
				+ "    /* Type ------------------------------ */\n" + "    \n" + "    h1 {\n" + "      margin-top: 0;\n"
				+ "      color: #2F3133;\n" + "      font-size: 19px;\n" + "      font-weight: bold;\n"
				+ "      text-align: left;\n" + "    }\n" + "    \n" + "    h2 {\n" + "      margin-top: 0;\n"
				+ "      color: #2F3133;\n" + "      font-size: 16px;\n" + "      font-weight: bold;\n"
				+ "      text-align: left;\n" + "    }\n" + "    \n" + "    h3 {\n" + "      margin-top: 0;\n"
				+ "      color: #2F3133;\n" + "      font-size: 14px;\n" + "      font-weight: bold;\n"
				+ "      text-align: left;\n" + "    }\n" + "    \n" + "    p {\n" + "      margin-top: 0;\n"
				+ "      color: #74787E;\n" + "      font-size: 16px;\n" + "      line-height: 1.5em;\n"
				+ "      text-align: left;\n" + "    }\n" + "    \n" + "    p.sub {\n" + "      font-size: 12px;\n"
				+ "    }\n" + "    \n" + "    p.center {\n" + "      text-align: center;\n" + "    }\n"
				+ "    </style>\n" + "  </head>\n" + "  <body>\n" + "    <span class=\"preheader\"></span>\n"
				+ "    <table class=\"email-wrapper\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n"
				+ "      <tr>\n" + "        <td align=\"center\">\n"
				+ "          <table class=\"email-content\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n"
				+ "            <tr>\n" + "              <td class=\"email-masthead\">\n"
				+ "                <a href=\"diphonline.org\" class=\"email-masthead_name\">DIPH  Application    </a>\n"
				+ "              </td>\n" + "            </tr>\n" + "            <!-- Email Body -->\n"
				+ "            <tr>\n"
				+ "              <td class=\"email-body\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n"
				+ "                <table class=\"email-body_inner\" align=\"center\" width=\"570\" cellpadding=\"0\" cellspacing=\"0\">\n"
				+ "                  <!-- Body content -->\n" + "                  <tr>\n"
				+ "                    <td class=\"content-cell\">\n" + "                      <h1>Hi " + Name
				+ ",</h1>\n"
				+ "                      <p>You recently requested at <b>Forgot  your password</b> for your  account.  <strong>This is  your password .</strong></p>\n"
				+ "                      <!-- Action -->\n"
				+ "                      <table class=\"body-action\" align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n"
				+ "                        <tr>\n" + "                          <td align=\"center\">\n"
				+ "                            <!-- Border based button\n"
				+ "                       https://litmus.com/blog/a-guide-to-bulletproof-buttons-in-email-design -->\n"
				+ "                            <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n"
				+ "                              <tr>\n" + "                                <td align=\"center\">\n"
				+ "                                  <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n"
				+ "                                    <tr>\n" + "                                      <td>\n"
				+ "                                        User ID\n" + "                                      </td>\n"
				+ "                                      <td style=\"color: red\">\n"
				+ "                                        \n" + user_id + "\n"
				+ "                                      </td>\n" + "                                    </tr>\n"
				+ "                                    <tr>\n" + "                                      <td>\n"
				+ "                                     Password\n" + "                                      </td>\n"
				+ "                                      <td   style=\"color: red\">\n"
				+ "                                        \n" + password + "\n"
				+ "                                      </td>\n" + "                                    </tr>\n"
				+ "                                  </table>\n" + "                                </td>\n"
				+ "                              </tr>\n" + "                            </table>\n"
				+ "                          </td>\n" + "                        </tr>\n"
				+ "                      </table>\n"
				+ "                      <p>For security, this request was received from a System device . please don't Share  this email or <a href=\"www.arogyakendra.org\">contact support</a> if you have questions.</p>\n"
				+ "                      <p>Thanks,\n" + "                        <br>The DIPH  Team</p>\n"
				+ "                      \n" + "                    </td>\n" + "                  </tr>\n"
				+ "                </table>\n" + "              </td>\n" + "            </tr>\n" + "            <tr>\n"
				+ "              <td>\n"
				+ "                <table class=\"email-footer\" align=\"center\" width=\"570\" cellpadding=\"0\" cellspacing=\"0\">\n"
				+ "                  <tr>\n" + "                    <td class=\"content-cell\" align=\"center\">\n"
				+ "                      <p class=\"sub align-center\">&copy; 2020 DIPH Team . All rights reserved.</p>\n"
				+ "                      <p class=\"sub align-center\">\n" + "                      \n"
				+ "                      <b>DIPH is developed by the London School of Hygiene and Tropical Medicine, with IT support by Tattva foundation</b>\n"
				+

				"                       \n" + "                        \n" + "                      </p>\n"
				+ "                    </td>\n" + "                  </tr>\n" + "                </table>\n"
				+ "              </td>\n" + "            </tr>\n" + "          </table>\n" + "        </td>\n"
				+ "      </tr>\n" + "    </table>\n" + "  </body>\n" + "</html>";

		return str;
	}
	
	@RequestMapping(path = "/updateuser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ManageCreateUserReturnStatusBean updateexistinguser(@RequestBody User_Info model) {
		ManageCreateUserReturnStatusBean response = new ManageCreateUserReturnStatusBean();
		
		
		try {
			
			response = dao.updateuser(model);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			sendPassword2(e.toString(), ""+e.getMessage(), "abhishek@tattvafoundation.org");
			response.setStatus("not_allowed");
			response.setMessage("Server Error");
		}
		
		return response;
	}

	// http://localhost:8080/diphonline/createnewuser

	@RequestMapping(path = "/createnewuser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ManageCreateUserReturnStatusBean createnewuser(@RequestBody User_Info model) {

		ManageCreateUserReturnStatusBean result = new ManageCreateUserReturnStatusBean();
		
		String encodedPassword = passwordEncoder.encode(model.getUser_password());
		model.setUser_password(encodedPassword);
		
		try {
			 result = dao.createuser(model);
		} catch (Exception e) {
			result.setStatus("not_allowed");
			result.setMessage("Server Error");
			e.printStackTrace();
			// TODO: handle exception
		}

		return result;
	}

	// http://localhost:8080/diphonline/viewExistingUsers

	@RequestMapping(path = "/viewExistingUsers", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ViewExistingUsers viewExistingUsers(@RequestBody ViewUsers_UserBean model) {

		ViewExistingUsers response = new ViewExistingUsers();
		try {

			response = dao.viewusers(model.getLoggedUser(), model.getLoggedUserDistrict());
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus("false");
			response.setMessage("Server Error occured");
			response.setUserList(new ArrayList<>());
			// TODO: handle exception
		}

		return response;
	}

	// http://localhost:8080/diphonline/deleteuser
	@RequestMapping(path = "/deleteuser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ManageCreateUserReturnStatusBean deleteuser(@RequestBody DeleteUserBean model) {

		ManageCreateUserReturnStatusBean result = new ManageCreateUserReturnStatusBean();

		try {
			result = dao.deleteuser(model);
		} catch (Exception e) {
			result.setStatus("not_allowed");
			result.setMessage("Server Error");
			e.printStackTrace();
			// TODO: handle exception
		}

		return result;
	}

}