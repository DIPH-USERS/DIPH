package com.tattvafoundation.diphonline.repository;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
//import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tattvafoundation.diphonline.controller.ForgotPasswordBean;
import com.tattvafoundation.diphonline.model.APPLoginBean;
import com.tattvafoundation.diphonline.model.APPLoginPostParameterBean_NEW;
import com.tattvafoundation.diphonline.model.ChangePasswordBean;
import com.tattvafoundation.diphonline.model.ContactFormDetailsBean;
import com.tattvafoundation.diphonline.model.CurrentCycleStatus;
import com.tattvafoundation.diphonline.model.Cycle;
import com.tattvafoundation.diphonline.model.District;
import com.tattvafoundation.diphonline.model.FeedbackDetailsBean;
import com.tattvafoundation.diphonline.model.LockCycleStatusBean;
//import com.tattvafoundation.diphonline.model.Customer;
import com.tattvafoundation.diphonline.model.Login;
import com.tattvafoundation.diphonline.model.UserCredentialsFromAndroidBean;
import com.tattvafoundation.diphonline.model.UserDistrictCycleYearList;
import com.tattvafoundation.diphonline.model.Userallowedstatus;
import com.tattvafoundation.diphonline.model.Year;

import org.springframework.security.crypto.password.PasswordEncoder;

@Repository
public class LoginDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public String sendcontactformMail(ContactFormDetailsBean model) {

		String status = "0";

		String usertype = "General User";

		if ("0".equals(model.getRegisteredUser())) {
			usertype = "General User";
		} else {
			usertype = "Registered User";
		}

		status = sendContactFormDetailstoMail(usertype, model.getUsername(), "" + model.getSelectedcountry(),
				"" + model.getSelecteddistrict(), "" + model.getOrganization_n(), model.getAddress_n(),
				model.getMobile_n(), "" + model.getSubject_n(), model.getMessage_desc(), "" + model.getEmail());

		return status;
	}

	public String sendContactFormDetailstoMail(String registeredUser, String user, String countryname,
			String districtname, String organization, String address, String mobile, String subject, String message,
			String user_email) {

		String host = "smtp.gmail.com";
		final String username = "lshtm.diph@gmail.com";// change accordingly
		final String password = "desktop@123";// change accordingly

		// String to="lshtm.diph@gmail.com";//change accordingly

		String result = "0";

		try {

			String recipient = "lshtm.diph@gmail.com";

			Properties props = new Properties();

			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.from", "myemail@gmail.com");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.port", "587");
			props.setProperty("mail.debug", "true");

			Session session = Session.getInstance(props, null);
			MimeMessage msg = new MimeMessage(session);

			msg.setRecipients(Message.RecipientType.TO, recipient);
			msg.setSubject("Contact Us [" + registeredUser + "]  -  " + subject);
			msg.setSentDate(new Date());
			msg.setText("Hello, world!\n");
			msg.setContent(ContactFormDetailsemailsContents("" + countryname, "" + districtname, organization, address,
					mobile, subject, message, user, user_email), "text/html");

			Transport transport = session.getTransport("smtp");

			transport.connect(username, password);
			transport.sendMessage(msg, msg.getAllRecipients());

			transport.close();

			result = "1";
		} catch (MessagingException e) {
			e.printStackTrace();
			result = "0";
		}

		return result;
	}

	public static String ContactFormDetailsemailsContents(String country, String district, String organization,
			String address, String mobile, String subject, String message, String Name, String user_email) {
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
				+ "                <table class=\"email-body_inner\" align=\"center\" width=\"1000\" cellpadding=\"0\" cellspacing=\"0\">\n"
				+ "                  <!-- Body content -->\n" + "                  <tr>\n"
				+ "                    <td class=\"content-cell\">\n" + "                      <h1>Hello Admin" + ""
				+ ",</h1>\n"
				+ "                      <p>The following user has tried to contact us, on our website.<strong>Please look forward into it..</strong></p>\n"
				+ "                      <!-- Action -->\n"
				+ "                      <table class=\"body-action\" align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n"
				+ "                        <tr>\n" + "                          <td align=\"center\">\n"
				+ "                            <!-- Border based button\n"
				+ "                       https://litmus.com/blog/a-guide-to-bulletproof-buttons-in-email-design -->\n"
				+ "                            <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n"
				+ "                              <tr>\n" + "                                <td >\n"
				+ "                                  <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n"
				+ "                                    <tr>\n" + "                                      <td>\n"
				+ "                                        Name : \n" + "                                      </td>\n"
				+ "                                      <td style=\"color: red\">\n"
				+ "                                        \n" + Name + "\n"
				+ "                                      </td>\n" + "                                    </tr>\n"
				+ "                                    <tr>\n" + "                                      <td>\n"
				+ "                                     Country : \n" + "                                      </td>\n"
				+ "                                      <td   style=\"color: red\">\n"
				+ "                                        \n" + country + "\n"
				+ "                                      </td>\n" + "                                    </tr>\n"
				+ "                                    <tr>\n" + "                                      <td>\n"
				+ "                                     District : \n" + "                                      </td>\n"
				+ "                                      <td   style=\"color: red\">\n"
				+ "                                        \n" + district + "\n"
				+ "                                      </td>\n" + "                                    </tr>\n"
				+ "                                    <tr>\n" + "                                      <td>\n"
				+ "                                     Organization : \n"
				+ "                                      </td>\n"
				+ "                                      <td   style=\"color: red\">\n"
				+ "                                        \n" + organization + "\n"
				+ "                                      </td>\n" + "                                    </tr>\n"
				+ "                                    <tr>\n" + "                                      <td>\n"
				+ "                                     Address : \n" + "                                      </td>\n"
				+ "                                      <td   style=\"color: red\">\n"
				+ "                                        \n" + address + "\n"
				+ "                                      </td>\n" + "                                    </tr>\n"
				+ "                                    <tr>\n" + "                                      <td>\n"
				+ "                                     Mobile : \n" + "                                      </td>\n"
				+ "                                      <td   style=\"color: red\">\n"
				+ "                                        \n" + mobile + "\n"
				+ "                                      </td>\n" + "                                    </tr>\n"
				+ "                                    <tr>\n" + "                                      <td>\n"
				+ "                                     Email : \n" + "                                      </td>\n"
				+ "                                      <td   style=\"color: red\">\n"
				+ "                                        \n" + user_email + "\n"
				+ "                                      </td>\n" + "                                    </tr>\n"
				+ "                                    <tr>\n" + "                                      <td>\n"
				+ "                                     Message : \n" + "                                      </td>\n"
				+ "                                      <td   style=\"color: red\">\n"
				+ "                                        \n" + message + "\n"
				+ "                                      </td>\n" + "                                    </tr>\n"
				+ "                                  </table>\n" + "                                </td>\n"
				+ "                              </tr>\n" + "                            </table>\n"
				+ "                          </td>\n" + "                        </tr>\n"
				+ "                      </table>\n"
				+ "                      <p>For security, this request was received from a System device . please don't Share  this email or <a href=\"diphonline.org\">contact support</a> if you have questions.</p>\n"
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

	public String sendfeedbackMail(FeedbackDetailsBean model) {

		String status = "0";

		String usertype = "General User";

		if ("0".equals(model.getRegisteredUser())) {
			usertype = "General User";
		} else {
			usertype = "Registered User";
		}

		status = sendFeedbackDetailstoMail(usertype, model.getUsername(), "" + model.getSelectedcountry(),
				"" + model.getSelecteddistrict(), "" + model.getSubject_n(), model.getFeedback_desc(),
				"" + model.getEmail());

		return status;
	}

	public String sendFeedbackDetailstoMail(String registeredUser, String user, String countryname, String districtname,
			String subject, String feedback, String user_mail) {

		String host = "smtp.gmail.com";
		final String username = "lshtm.diph@gmail.com";// change accordingly
		final String password = "desktop@123";// change accordingly

		// String to="lshtm.diph@gmail.com";//change accordingly

		String result = "0";

		try {

			String recipient = "lshtm.diph@gmail.com";

			Properties props = new Properties();

			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.from", "myemail@gmail.com");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.port", "587");
			props.setProperty("mail.debug", "true");

			Session session = Session.getInstance(props, null);
			MimeMessage msg = new MimeMessage(session);

			msg.setRecipients(Message.RecipientType.TO, recipient);
			msg.setSubject("Feedback [" + registeredUser + "]  -  " + subject);
			msg.setSentDate(new Date());
			msg.setText("Hello, world!\n");
			msg.setContent(
					FeedbackDetailsemailsContents("" + countryname, districtname, "" + user, feedback, user_mail),
					"text/html");

			Transport transport = session.getTransport("smtp");

			transport.connect(username, password);
			transport.sendMessage(msg, msg.getAllRecipients());

			transport.close();

			result = "1";
		} catch (MessagingException e) {
			e.printStackTrace();
			result = "0";
		}

		return result;
	}

	public static String FeedbackDetailsemailsContents(String country, String district, String Name, String feedback,
			String user_mail) {
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
				+ "                <table class=\"email-body_inner\" align=\"center\" width=\"1000\" cellpadding=\"0\" cellspacing=\"0\">\n"
				+ "                  <!-- Body content -->\n" + "                  <tr>\n"
				+ "                    <td class=\"content-cell\">\n" + "                      <h1>Hello Admin" + ""
				+ ",</h1>\n"
				+ "                      <p>The following user has sent a feedback on our website.<strong>Please look forward into it.</strong></p>\n"
				+ "                      <!-- Action -->\n"
				+ "                      <table class=\"body-action\" align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n"
				+ "                        <tr>\n" + "                          <td align=\"center\">\n"
				+ "                            <!-- Border based button\n"
				+ "                       https://litmus.com/blog/a-guide-to-bulletproof-buttons-in-email-design -->\n"
				+ "                            <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n"
				+ "                              <tr>\n" + "                                <td >\n"
				+ "                                  <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n"
				+ "                                    <tr>\n" + "                                      <td>\n"
				+ "                                        Name : \n" + "                                      </td>\n"
				+ "                                      <td style=\"color: red\">\n"
				+ "                                        \n" + Name + "\n"
				+ "                                      </td>\n" + "                                    </tr>\n"
				+ "                                    <tr>\n" + "                                      <td>\n"
				+ "                                     Country : \n" + "                                      </td>\n"
				+ "                                      <td   style=\"color: red\">\n"
				+ "                                        \n" + country + "\n"
				+ "                                      </td>\n" + "                                    </tr>\n"
				+ "                                    <tr>\n" + "                                      <td>\n"
				+ "                                     District : \n" + "                                      </td>\n"
				+ "                                      <td   style=\"color: red\">\n"
				+ "                                        \n" + district + "\n"
				+ "                                      </td>\n" + "                                    </tr>\n"
				+ "                                    <tr>\n" + "                                      <td>\n"
				+ "                                     Email : \n" + "                                      </td>\n"
				+ "                                      <td   style=\"color: red\">\n"
				+ "                                        \n" + user_mail + "\n"
				+ "                                      </td>\n" + "                                    </tr>\n"
				+ "                                    <tr>\n" + "                                      <td>\n"
				+ "                                     Feedback : \n" + "                                      </td>\n"
				+ "                                      <td   style=\"color: red\">\n"
				+ "                                        \n" + feedback + "\n"
				+ "                                      </td>\n" + "                                    </tr>\n"
				+ "                                  </table>\n" + "                                </td>\n"
				+ "                              </tr>\n" + "                            </table>\n"
				+ "                          </td>\n" + "                        </tr>\n"
				+ "                      </table>\n"
				+ "                      <p>For security, this request was received from a System device . please don't Share  this email or <a href=\"diphonline.org\">contact support</a> if you have questions.</p>\n"
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

	// private static final String SQL = "select * from user_info";

	public Login isauthenticated(String enteredUsername, String enteredPassword) {

		// For error wise login
		Login obj = new Login("_", enteredUsername, "not authentic", "0", -1);
		
		String query = "select * from user_info where user_nm = ?";

		List<Map<String, Object>> rows = jdbcTemplate.queryForList(query, new Object[]{ enteredUsername });

		for (Map<String, Object> row : rows) {

			String passwordInDB = (String) row.get("user_pass");		
			
			if (passwordEncoder.matches(enteredPassword, passwordInDB)) {

				obj = new Login();
				obj.setPassword((String) row.get("user_pass"));
				obj.setUsername((String) row.get("user_nm"));
				obj.setUserStatus("" + (Object) row.get("user_status"));
				obj.setStatus("authentic");
				obj.setId((Integer) row.get("user_id"));
				break;
			}

		}

		return obj;

	}

	public UserDistrictCycleYearList getAllowedDistrictsCycleYear_NEW(UserCredentialsFromAndroidBean model) {

		String sql = "select * from user_info where user_nm=?";

		Object[] params = new Object[] { model.getLoggedinUserId() };

		String userid = jdbcTemplate.query(sql, params, rs -> {
			String temp = "-1";
			while (rs.next()) {
				temp = "" + rs.getInt("user_id");
			}
			return temp;
		});
		
		
		String sql_check = "SELECT `id`,    `user_info_id`,    `district_id`,    `cycle_id`,    `year` FROM `geographical_combination` where (`user_info_id` = ?);";
		Object[] params_check = new Object[] { userid };

		//final Login correct_obj = obj;

		UserDistrictCycleYearList respose = jdbcTemplate.query(sql_check, params_check, rs -> {

			UserDistrictCycleYearList temp_inst = new UserDistrictCycleYearList(new ArrayList<>(),new ArrayList<>(),new ArrayList<>() );

			Set<String> tree_Set1 = new TreeSet<String>();
			Set<String> tree_Set2 = new TreeSet<String>();
			Set<String> tree_Set3 = new TreeSet<String>(); 
			
			while (rs.next()) {
				
				tree_Set1.add(rs.getString("district_id"));
				tree_Set2.add(rs.getString("cycle_id"));
				tree_Set3.add(rs.getString("year"));
				
				
				
				//temp_inst.setDistrictList(new ArrayList<String>(tree_Set1));
				//temp_inst.setCyclesList(new ArrayList<String>(tree_Set2));
				//temp_inst.setYearList(new ArrayList<String>(tree_Set3)); 
				
				// String keyval = rs.getString("form_2_id");
				//temp_obj = correct_obj;
			}
			
			List<String> dlist = new ArrayList<String>(tree_Set1);
			List<String> clist = new ArrayList<String>(tree_Set2);
			List<String> ylist = new ArrayList<String>(tree_Set3);
			
			List<District> dl = new ArrayList<>();
			
			List<Cycle> cl = new ArrayList<>();
			
			List<Year> yl = new ArrayList<>();
			
			for(int i=0;i<dlist.size();i++) {
				District d = new District();
				d.setDistrict_id(Integer.parseInt(dlist.get(i)));
				dl.add(d);
			}
			
			for(int i=0;i<clist.size();i++) {
				Cycle c = new Cycle();
				c.setCycle_id(Integer.parseInt(clist.get(i))); 
				cl.add(c);
			}
			
			
			for(int i=0;i<ylist.size();i++) {
				Year y = new Year();
				y.setYear_id(ylist.get(i));
				yl.add(y);
			}
			
			temp_inst.setDistrictList(dl);
			temp_inst.setCyclesList(cl);
			temp_inst.setYearList(yl);  
			
			return temp_inst;
		});
		
		//In case if user is admin then his district allotted is 0. Also he will have only 1 district i.e. 0.
		if((respose.getDistrictList().size() == 1)  &&  (respose.getDistrictList().get(0).getDistrict_id() == 0)) {
	
			 String SQL_districts = "SELECT d.`district_id`,d.`country_id`,d.`district_name`,d.`state_id`,s.`region_id` FROM `district` d inner join `country_states` s on d.state_id=s.cs_id ;";
				
				Object[] params_districts = new Object[] {  }; 

				List<District> dlist = jdbcTemplate.query(SQL_districts, params_districts, rs -> {

					List<District> list = new ArrayList<>();
					while (rs.next()) {
						
						District d = new District();
						
						d.setDistrict_id(Integer.parseInt(rs.getString("district_id"))); 
						d.setDistrict_name(rs.getString("district_name"));
						d.setCountry_id(Integer.parseInt(rs.getString("country_id")));
						d.setState_id(rs.getString("state_id"));
						d.setRegion_id(rs.getString("region_id")); 
						
						list.add(d); 
					}
					/* We can also return any variable-data from here but not used currently */
					return list;
				});
				
				respose.setDistrictList(dlist);  
			
			
//			 String SQL_districts = "SELECT `district_id` FROM `district`";
//				
//				Object[] params_districts = new Object[] {  };
//
//				List<String> dlist = jdbcTemplate.query(SQL_districts, params_districts, rs -> {
//
//					List<String> list = new ArrayList<>();
//					while (rs.next()) {
//						
//						list.add(rs.getString("district_id"));
//					}
//					
//					return list;
//				});
				
				
				
				 String SQL_cycles = "SELECT `cycle_id`,   `cycle_name` FROM `cycle`;";
					
					Object[] params_cycles = new Object[] {  }; 

					List<Cycle> clist =	jdbcTemplate.query(SQL_cycles, params_cycles, rs -> {

						List<Cycle> list = new ArrayList<>();
						while (rs.next()) {
							
							Cycle c = new Cycle();
							
							c.setCycle_id(Integer.parseInt(rs.getString("cycle_id")));
							c.setCycle_name(rs.getString("cycle_name")); 
							
							
							list.add(c); 
						}
						/* We can also return any variable-data from here but not used currently */
						return list;
					});
				
					respose.setCyclesList(clist);
					
					
//				 String SQL_cycle = "SELECT * FROM `cycle`";
//					
//					Object[] params_cycle = new Object[] {  };
//
//					List<String> clist = jdbcTemplate.query(SQL_cycle, params_cycle, rs -> {
//
//						List<String> list = new ArrayList<>();
//						while (rs.next()) {
//							
//							list.add(rs.getString("cycle_id"));
//						}
//						
//						return list;
//					});
//					
//					respose.setCycle_list(clist);
					
					
					
					 LocalDateTime currentTime = LocalDateTime.now();
				      
						
				      LocalDate date1 = currentTime.toLocalDate(); 
				      
				      List<Year> ylist = new ArrayList<>(); 
				      
				      
				      for(int i=date1.getYear()+1, index=0;i>=2020;i--,index++) {
				    	  
				    	  String s = ""+i;
				    	  
				    	  Year yy = new Year();
				    	  
				    	  yy.setYear_id(""+i);
				    	  yy.setYear_name(""+i); 
							
							ylist.add(yy);  
				      }
				      
				      
				      respose.setYearList(ylist);   
				     
			
		}
		else {
			
			List<District> dlist = respose.getDistrictList(); 
			
			String dstr = "";
			
			for(int i=0;i<dlist.size();i++) {
				int s = dlist.get(i).getDistrict_id();
				
				if(i==0) {
					dstr =""+ s;
				}
				else {
					dstr = dstr + ","+s;	
				}
				
			}
			
			
			
			 String SQL_districts = "SELECT d.`district_id`,d.`country_id`,d.`district_name`,d.`state_id`,s.`region_id` FROM `district` d inner join `country_states` s on d.state_id=s.cs_id  where `district_id` IN ("+dstr+");";
				
				Object[] params_districts = new Object[] {  }; 

				dlist = jdbcTemplate.query(SQL_districts, params_districts, rs -> {

					List<District> list = new ArrayList<>();
					while (rs.next()) {
						
						District d = new District();
						
						d.setDistrict_id(Integer.parseInt(rs.getString("district_id"))); 
						d.setDistrict_name(rs.getString("district_name"));
						d.setCountry_id(Integer.parseInt(rs.getString("country_id")));
						d.setState_id(rs.getString("state_id"));
						d.setRegion_id(rs.getString("region_id")); 
						
						list.add(d); 
					}
					/* We can also return any variable-data from here but not used currently */
					return list;
				});
				
				
				List<Cycle> clist = respose.getCyclesList();
				
				String cstr = "";
				
				for(int i=0;i<clist.size();i++) {
					int s = clist.get(i).getCycle_id();
					
					if(i==0) {
						cstr =""+ s;
					}
					else {
						cstr = cstr + ","+s;	
					}
					
				}
				
				
				 String SQL_cycles = "SELECT `cycle_id`,   `cycle_name` FROM `cycle` where  `cycle_id` IN ("+cstr+");";
					
					Object[] params_cycles = new Object[] {  }; 

					clist =	jdbcTemplate.query(SQL_cycles, params_cycles, rs -> {

						List<Cycle> list = new ArrayList<>();
						while (rs.next()) {
							
							Cycle c = new Cycle();
							
							c.setCycle_id(Integer.parseInt(rs.getString("cycle_id")));
							c.setCycle_name(rs.getString("cycle_name")); 
							
							
							list.add(c); 
						}
						/* We can also return any variable-data from here but not used currently */
						return list;
					});
					
					
					
					List<Year> ylist = respose.getYearList();
					
					for(int i=0;i<ylist.size();i++) {
						String s = ylist.get(i).getYear_id();
						
						ylist.get(i).setYear_name(s); 
					}		
				
				respose.setDistrictList(dlist);
				
				respose.setCyclesList(clist); 
				
				respose.setYearList(ylist); 
		}

		return respose;
	}

	public APPLoginBean isauthenticatedForGeographicalCombo_NEW(UserCredentialsFromAndroidBean model) {

		APPLoginBean response = new APPLoginBean();

		// For error wise login
		Login obj = new Login("_", model.getLoggedinUserId(), "not authentic", "0", -1);

		String sql = "select * from user_info where user_nm=?";

		Object[] params = new Object[] { model.getLoggedinUserId() };

		obj = jdbcTemplate.query(sql, params, rs -> {

			Login temp_obj = new Login("_", model.getLoggedinUserId(), "not authentic", "0", -1);
			while (rs.next()) {
				temp_obj.setPassword(rs.getString("user_pass"));
				temp_obj.setUsername(rs.getString("user_nm"));
				temp_obj.setUserStatus(rs.getString("user_status"));
				temp_obj.setStatus("authentic");
				temp_obj.setId(rs.getInt("user_id"));
			}

			return temp_obj;
		});

		List<Login> temp_list = new ArrayList<>();
		temp_list.add(obj);

		response.setResult(temp_list);

		if (-1 == obj.getId()) {
			response.setError_code("100");
			response.setMessage("Invalid Login Credentials");
		} else {
			response.setError_code("200");
			response.setMessage("Correct Login Authentication Status");

			// Check for admin first

			if ("1".equals(obj.getUserStatus())) {
				// Admin found,So all clear.

				// I repeat All clear
			} else {

				String sql_check = "SELECT * FROM geographical_combination where (user_info_id = ? and district_id = ? );";
				Object[] params_check = new Object[] { obj.getId(), model.getDistrict_id() };

				final Login correct_obj = obj;

				Login new_obj = jdbcTemplate.query(sql_check, params_check, rs -> {

					Login temp_obj = new Login("_", model.getLoggedinUserId(), "not authentic", "0", -1);

					while (rs.next()) {
						// String keyval = rs.getString("form_2_id");
						temp_obj = correct_obj;
					}
					return temp_obj;
				});

				if (-1 == new_obj.getId()) {
					response.setError_code("100");
					response.setMessage("User not allowed for this Geograpical Combination");
				} else {
					response.setError_code("200");
					response.setMessage("Correct Login Authentication Status");
				}

			}

		}

		return response;

	}

	public APPLoginBean isauthenticated_NEW(APPLoginPostParameterBean_NEW model) {

		APPLoginBean response = new APPLoginBean();

		// For error wise login
		Login obj = new Login("_", model.getUsername(), "not authentic", "0", -1);

		String sql = "select * from user_info where user_nm=? and user_pass=?";
		// String sql_check = "SELECT * FROM form_2_filled_by where `district_id`=? and
		// `cycle_id`=? and `financial_year`=?";
		// Object[] params_check = new Object[] { tempobj.getDistrict_id(),
		// tempobj.getCycle_id(), tempobj.getYear() };

		Object[] params = new Object[] { model.getUsername(), model.getPassword() };

		obj = jdbcTemplate.query(sql, params, rs -> {

			Login temp_obj = new Login("_", model.getUsername(), "not authentic", "0", -1);
			while (rs.next()) {
				temp_obj.setPassword(rs.getString("user_pass"));
				temp_obj.setUsername(rs.getString("user_nm"));
				temp_obj.setUserStatus(rs.getString("user_status"));
				temp_obj.setStatus("authentic");
				temp_obj.setId(rs.getInt("user_id"));
			}

			return temp_obj;
		});

		List<Login> temp_list = new ArrayList<>();
		temp_list.add(obj);

		response.setResult(temp_list);

		if (-1 == obj.getId()) {
			response.setError_code("100");
			response.setMessage("Invalid Login Credentials");
		} else {
			response.setError_code("200");
			response.setMessage("Correct Login Authentication Status");

			// Check for admin first

			if ("1".equals(obj.getUserStatus())) {
				// Admin found,So all clear.

				// I repeat All clear
			} else {

				String sql_check = "SELECT * FROM geographical_combination where (user_info_id = ? and district_id = ? and cycle_id = ? and year = ?);";
				Object[] params_check = new Object[] { obj.getId(), model.getDistrict_id(), model.getCycle(),
						model.getYear() };

				final Login correct_obj = obj;

				Login new_obj = jdbcTemplate.query(sql_check, params_check, rs -> {

					Login temp_obj = new Login("_", model.getUsername(), "not authentic", "0", -1);

					while (rs.next()) {
						// String keyval = rs.getString("form_2_id");
						temp_obj = correct_obj;
					}
					return temp_obj;
				});

				if (-1 == new_obj.getId()) {
					response.setError_code("100");
					response.setMessage("User not allowed for this Geograpical Combination");
				} else {
					response.setError_code("200");
					response.setMessage("Correct Login Authentication Status");
				}

			}
			// SELECT * FROM geographical_combination where (user_info_id = "2"
			// and district_id = "1" and cycle_id = "1" and year = "2020");
		}

		/*
		 * List<Map<String, Object>> rows = jdbcTemplate.queryForList(
		 * "select * from user_info where user_nm='" + model.getUsername() +
		 * "' and user_pass='" + model.getPassword() + "'");
		 * 
		 * for (Map<String, Object> row : rows) { obj = new Login();
		 * obj.setPassword((String) row.get("user_pass")); obj.setUsername((String)
		 * row.get("user_nm")); obj.setUserStatus("" + (Object) row.get("user_status"));
		 * obj.setStatus("authentic"); obj.setId((Integer) row.get("user_id"));
		 * 
		 * }
		 */

		return response;

	}

	public Userallowedstatus processcurrentuserallowedstatus(String username, String district_id, String cycle_id,
			String year) {

		Userallowedstatus response = new Userallowedstatus();

		String user_existing_p_key = jdbcTemplate.query("SELECT * from  user_info  WHERE `user_nm` = ?",
				new Object[] { username }, rs2 -> {

					String pkey = "";

					while (rs2.next()) {
						pkey = rs2.getString("user_id");
					}
					/* We can also return any variable-data from here but not used currently */
					return pkey;
				});

		
		String flagadmin = jdbcTemplate.query("SELECT * from  user_info  WHERE `user_nm` = ?",
				new Object[] { username }, rs2 -> {

					String admincheck = "";

					while (rs2.next()) {
						admincheck = rs2.getString("user_status");
					}
					/* We can also return any variable-data from here but not used currently */
					return admincheck;
				});

		if ("1".equals(flagadmin)) {
		
			response.setAllowed("1");
			response.setMessage("Admin User");
		} else {			

			String checkpkey = jdbcTemplate.query(
					"SELECT `id`,    `user_info_id`,    `district_id`,    `cycle_id`,    `year`  FROM  `geographical_combination` where `user_info_id`=? and `district_id`=?  and  `cycle_id`=?  and   `year`=?",
					new Object[] { user_existing_p_key, district_id, cycle_id, year }, rs2 -> {

						String pkey = "";

						while (rs2.next()) {
							pkey = rs2.getString("id");

						}
						/* We can also return any variable-data from here but not used currently */
						return pkey;
					});

			if ("".equals(checkpkey)) {

	
				response.setAllowed("0");
				response.setMessage("District User");
			} else {
		
				response.setAllowed("1");
				response.setMessage("District User");
			}

		}

		return response;
	}

	public CurrentCycleStatus cyclelockstatus(String district_id, String cycle_id, String year) {

		CurrentCycleStatus response = new CurrentCycleStatus();

		String status = jdbcTemplate.query(
				"SELECT `id`,    `district`,    `cycle`,    `year`,    `recordcreated`, "
						+ "   `updateddate`,    `status`,    `userid` FROM  `allforms_cycle_lock_status`  "
						+ "   where `district`=? and `cycle`=? and `year`=?",

				new Object[] { district_id, cycle_id, year }, rs2 -> {

					String str = "";

					while (rs2.next()) {
						str = rs2.getString("status");
					}
					/* We can also return any variable-data from here but not used currently */
					return str;
				});

		if ("".equals(status)) {

			response.setStatus("0");
			response.setMessage("Cycle not Locked");
		} else {

			if ("0".equals(status)) {
				response.setStatus("0");
				response.setMessage("Cycle not Locked");
			} else {
				response.setStatus("1");
				response.setMessage("Cycle Locked");
			}

		}

		return response;
	}

	public LockCycleStatusBean unlockcycledao(String district_id, String cycle_id, String year, String user_id) {

		LockCycleStatusBean response = new LockCycleStatusBean();

		// will give us the current time and date
		LocalDateTime current = LocalDateTime.now();

		// to print in a particular format
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		String formatedDateTime = current.format(format);

		

		String sql2 = "UPDATE `allforms_cycle_lock_status` SET `updateddate` = ?,`status` = ?,"
				+ "   `userid` = ? WHERE `district` = ? and `cycle` = ? and `year` = ?";

		int row = jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql2);
			ps.setString(1, formatedDateTime);
			ps.setString(2, "0");
			ps.setString(3, user_id);
			ps.setString(4, district_id);
			ps.setString(5, cycle_id);
			ps.setString(6, year);
			return ps;
		});

		if (row > 0) {
			
			response.setStatus("1");
			response.setMessage("Table Unlocked successfully");
		} else {
			
			response.setStatus("0");
			response.setMessage("Server Error");
		}

		return response;

	}

	public LockCycleStatusBean lockcycledao(String district_id, String cycle_id, String year, String user_id) {

		LockCycleStatusBean response = new LockCycleStatusBean();

		// will give us the current time and date
		LocalDateTime current = LocalDateTime.now();

		// to print in a particular format
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		String formatedDateTime = current.format(format);

		String primarykey = jdbcTemplate.query(
				"SELECT `id`,    `district`,    `cycle`,    `year`,    `recordcreated`, "
						+ "   `updateddate`,    `status`,    `userid` FROM  `allforms_cycle_lock_status`  "
						+ "   where `district`=? and `cycle`=? and `year`=?",

				new Object[] { district_id, cycle_id, year }, rs2 -> {

					String pkey = "";

					while (rs2.next()) {
						pkey = rs2.getString("id");
					}
					/* We can also return any variable-data from here but not used currently */
					return pkey;
				});

		if ("".equals(primarykey)) {

		
			String sql_insert = "INSERT INTO `allforms_cycle_lock_status` (`district`,`cycle`,`year`,`recordcreated`,"
					+ "`updateddate`,`status`,`userid`) VALUES (?,?,?,?,?,?,?);";

			int row = jdbcTemplate.update(connection -> {

				PreparedStatement ps = connection.prepareStatement(sql_insert);
				ps.setString(1, district_id);
				ps.setString(2, cycle_id);
				ps.setString(3, year);
				ps.setString(4, formatedDateTime);
				ps.setString(5, formatedDateTime);
				ps.setString(6, "1");
				ps.setString(7, user_id);
				return ps;
			});

			if (row > 0) {
				response.setStatus("1");
				response.setMessage("Table locked successfully");
			} else {
			
				response.setStatus("0");
				response.setMessage("Server Error");
			}

		} else {
			

			String sql2 = "UPDATE `allforms_cycle_lock_status` SET `updateddate` = ?,`status` = ?,"
					+ "   `userid` = ? WHERE `district` = ? and `cycle` = ? and `year` = ?";

			int row = jdbcTemplate.update(connection -> {
				PreparedStatement ps = connection.prepareStatement(sql2);
				ps.setString(1, formatedDateTime);
				ps.setString(2, "1");
				ps.setString(3, user_id);
				ps.setString(4, district_id);
				ps.setString(5, cycle_id);
				ps.setString(6, year);
				return ps;
			});

			if (row > 0) {
			
				response.setStatus("1");
				response.setMessage("Table locked successfully");
			} else {
				
				response.setStatus("0");
				response.setMessage("Server Error");
			}
		}

		return response;
	}

	public ChangePasswordBean changeoldPasswordOfUser(ChangePasswordBean model) {

		ChangePasswordBean response = new ChangePasswordBean();

		
		response.setResult("100");

		String sql1 = " UPDATE `user_info` SET  `user_pass` = ?  WHERE `user_nm` = ? and `user_pass` = ?";

		int row = 0;

		row = jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql1);
			ps.setString(1, model.getConfirm_password());
			ps.setString(2, model.getUsername());
			ps.setString(3, model.getOld_password());
			return ps;
		});

		if (row > 0) {
			response.setResult("200");
			response.setMessage("Password Changes Successfully!!!");
		} else {
			response.setMessage("Bad User Credentials!!");
		}

		return response;
	}

	public ForgotPasswordBean sendPasswordToUser(String username) {

	

		ForgotPasswordBean data = new ForgotPasswordBean();

		data.setResult("100");

		String sql = "SELECT `user_id`, `user_nm`, `user_pass`,`district_id`, `can_create`, `can_view`, `can_edit`, `can_delete`,`emailId`  FROM `user_info` where `user_nm`=?";
		Object[] params = new Object[] { username };

		data = jdbcTemplate.query(sql, params, rs -> {

			ForgotPasswordBean obj = new ForgotPasswordBean();
			while (rs.next()) {
				obj.setUsername(rs.getString("user_nm"));
				obj.setPassword(rs.getString("user_pass"));
				obj.setEmail(rs.getString("emailId"));
				obj.setResult("200");
			}
			/* We can also return any variable-data from here but not used currently */
			return obj;
		});

		if ("200".equals(data.getResult())) {
			sendPassword2(data.getUsername(), data.getPassword(), data.getEmail());
		} else {
			data.setMessage("Invalid userid");
		}

		return data;
	}

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

	public void send(String to, String user_id, String passwords, String name) {

		String sub = "User Login Credential";
		String from = "abhishek@tattvafoundation.org";
		String password = "desktop@123";

		// int result=0;
		// Get properties object
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		// get Session
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		});
		// compose message
		try {

			MimeMessage message = new MimeMessage(session);
			message.setContent(emailsContents(user_id, passwords, name), "text/html");

			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(sub);
			// message.setText(msg);

			// send message
			Transport.send(message);
			// result=1;
		} catch (MessagingException e) {
			e.printStackTrace();
			// result=0;
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

}