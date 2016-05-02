package controller;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by  Nigel on 2016/4/25.
 */
public class sendCode extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter writer = response.getWriter();
        String tel = request.getParameter("tel");
        System.out.println("tel: " + tel);

        String appKey = "23340983";
        String appSecret = "bf2aa5dbee56947b9f910f3f6445b099";
        String apiUrl = "http://gw.api.taobao.com/router/rest";
        String signName = "注册验证";

        TaobaoClient client = new DefaultTaobaoClient(apiUrl, appKey, appSecret);
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setExtend("123456");
        req.setSmsType("normal");
        req.setSmsFreeSignName(signName);

        String code = genCode();
        System.out.println("code: " + code);
        request.getSession().setAttribute("code", code);
        req.setSmsParamString("{\"code\":\"【" + code + "】\",\"product\":\"【FantasticShow】\"}");
        req.setRecNum(tel);
        req.setSmsTemplateCode("SMS_3145362");
        AlibabaAliqinFcSmsNumSendResponse rsp;
        try {
            rsp = client.execute(req);
        } catch (ApiException e) {
            writer.print(
                    "{\"alibaba_aliqin_fc_sms_num_send_response\":{\"result\":{\"err_code\":\"100\"," + "\"model\":\"101375979671^1101891478301\",\"success\":false},\"request_id\":\"qm3cj8ckbec0\"}}");
            return;
        }
        String ret = rsp.getBody();
        System.out.println(ret);
        writer.print(ret);
    }

    String genCode() {

        StringBuilder code = new StringBuilder(4);
        for (int i = 0; i < 4; ++i) {
            code.append((int) (Math.random() * 10));
        }
        return code.toString();
    }
}
