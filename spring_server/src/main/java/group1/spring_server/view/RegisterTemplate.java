package group1.spring_server.view;

public class RegisterTemplate {


    private static String html = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<body>\n" +
            "\n" +
            "<form action=\"welcomepage\">\n" +
            "  Username:<br>\n" +
            "  <input type=\"text\" name=\"user\">\n" +
            "  <br>\n" +
            "  Password:<br>\n" +
            "  <input type=\"text\" name=\"pass\" >\n" +
            "  <br><br>\n" +
            "  Mail:<br>\n" +
            "  <input type=\"text\" name=\"mail\" >\n" +
            "  <br><br>\n" +
            "  <input type=\"submit\" value=\"Submit\">\n" +
            "</form> \n" +
            "\n" +
            "\n" +
            "</body>\n" +
            "</html>";


    public static String getHtml() {
        return html;
    }
}
