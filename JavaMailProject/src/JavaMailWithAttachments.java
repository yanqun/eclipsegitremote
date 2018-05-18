import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message.RecipientType;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class JavaMailWithAttachments {

    // �����˵� ���� �� ���루�滻Ϊ�Լ�����������룩
    public static String sendEmailAccount = "97971518@qq.com";
    public static String sendEmailPwd = "ujmhtlopbtjobjcf";

    // ����������� SMTP ��������ַ, ����׼ȷ, ��ͬ�ʼ���������ַ��ͬ, һ���ʽΪ: smtp.xxx.com
    // ����163����� SMTP ��������ַΪ: smtp.163.com
    public static String myEmailSMTPHost = "smtp.qq.com";

    // �ռ������䣨�滻Ϊ�Լ�֪������Ч���䣩
    public static String receiveMailAccount = "157468995@qq.com";

    public static void main(String[] args) throws Exception {
        // 1. ������������, ���������ʼ��������Ĳ�������
        Properties props = new Properties();                    // ��������
        props.setProperty("mail.transport.protocol", "smtp");   // ʹ�õ�Э�飨JavaMail�淶Ҫ��
        props.setProperty("mail.smtp.host", myEmailSMTPHost);   // �����˵������ SMTP ��������ַ
        props.setProperty("mail.smtp.auth", "true");            // ��Ҫ������֤
        
        final String smtpPort = "465";
        props.setProperty("mail.smtp.port", smtpPort);
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", smtpPort);

        // ���� SSL ����, �Լ�����ϸ�ķ��Ͳ����뿴��һƪ: ���� JavaMail �� Java �ʼ����ͣ����ʼ�����

        // 2. �������ô����Ự����, ���ں��ʼ�����������
        Session session = Session.getInstance(props);
        session.setDebug(true);                                 // ����Ϊdebugģʽ, ���Բ鿴��ϸ�ķ��� log

        // 3. ����һ���ʼ�
        MimeMessage message = createMimeMessage(session, sendEmailAccount, receiveMailAccount);

        // Ҳ���Ա��ֵ����ز鿴
        // message.writeTo(file_out_put_stream);

        // 4. ���� Session ��ȡ�ʼ��������
        Transport transport = session.getTransport();

        // 5. ʹ�� �����˺� �� ���� �����ʼ�������
        //    ������֤����������� message �еķ���������һ�£����򱨴�
        transport.connect(sendEmailAccount, sendEmailPwd);

        // 6. �����ʼ�, �������е��ռ���ַ, message.getAllRecipients() ��ȡ�������ڴ����ʼ�����ʱ��ӵ������ռ���, ������, ������
        transport.sendMessage(message, message.getAllRecipients());
        // 7. �ر�����
        transport.close();
    }

    /**
     * ����һ�⸴���ʼ����ı�+ͼƬ+������
     */
    public static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail) throws Exception {
		// 1. �����ʼ�����
		MimeMessage message = new MimeMessage(session);
		// 2. From: ������
		message.setFrom(new InternetAddress(sendMail, "���ǳ�", "UTF-8"));
		// 3. To: �ռ��ˣ��������Ӷ���ռ��ˡ����͡����ͣ�
		message.addRecipient(RecipientType.TO, new InternetAddress(receiveMail, "�ҵ��ռ����ǳ�", "UTF-8"));
		// 4. Subject: �ʼ�����
		message.setSubject("TͼƬ+������", "UTF-8");
		
		
		// 5. ����ͼƬ���ڵ㡱
		MimeBodyPart imagePart = new MimeBodyPart();
		DataHandler imageDataHandler = new DataHandler(new FileDataSource("src//abc.png")); // ��ȡ�����ļ�
		imagePart.setDataHandler(imageDataHandler);                   // ��ͼƬ������ӵ����ڵ㡱
		imagePart.setContentID("image_fairy_tail");     // Ϊ���ڵ㡱����һ��Ψһ��ţ����ı����ڵ㡱�����ø�ID��
		// 6. �����ı����ڵ㡱
		MimeBodyPart textPart = new MimeBodyPart();
		//    �������ͼƬ�ķ�ʽ�ǽ�����ͼƬ�������ʼ�������, ʵ����Ҳ������ http ���ӵ���ʽ�������ͼƬ
		textPart.setContent("����һ��ͼƬ<br/><img src='cid:image_fairy_tail'/>", 
						"text/html;charset=UTF-8");
		
		// 7. ���ı�+ͼƬ������ �ı� �� ͼƬ ���ڵ㡱�Ĺ�ϵ���� �ı� �� ͼƬ ���ڵ㡱�ϳ�һ����ϡ��ڵ㡱��
		MimeMultipart mm_text_image = new MimeMultipart();
		mm_text_image.addBodyPart(textPart);
		mm_text_image.addBodyPart(imagePart);
		mm_text_image.setSubType("related");    // ������ϵ
		
		// 8. �� �ı�+ͼƬ �Ļ�ϡ��ڵ㡱��װ��һ����ͨ���ڵ㡱
		MimeBodyPart text_image = new MimeBodyPart();
		text_image.setContent(mm_text_image);
		// 9. �����������ڵ㡱
		MimeBodyPart attachment = new MimeBodyPart();
		DataHandler attDataHandler = new DataHandler
					(new FileDataSource("src//�Ͽ�ǰ����ͷ.txt"));  // ��ȡ�����ļ�
		attachment.setDataHandler(attDataHandler);                                             // ������������ӵ����ڵ㡱
		attachment.setFileName(MimeUtility.encodeText(attDataHandler.getName()));              // ���ø������ļ�������Ҫ���룩
		// 10. ���ã��ı�+ͼƬ���� ���� �Ĺ�ϵ���ϳ�һ����Ļ�ϡ��ڵ㡱 / Multipart ��
		MimeMultipart mm = new MimeMultipart();
		mm.addBodyPart(text_image);
		mm.addBodyPart(attachment);     // ����ж�����������Դ������������
		mm.setSubType("mixed");         // ��Ϲ�ϵ
		
		// 11. ���������ʼ��Ĺ�ϵ�������յĻ�ϡ��ڵ㡱��Ϊ�ʼ���������ӵ��ʼ�����
		message.setContent(mm);
		
		
		// 12. ���÷���ʱ��
		message.setSentDate(new Date());
		
		// 13. �����������������
		message.saveChanges();
		
		return message;
    }

}