package controller;


import model.bean.User;

import javax.servlet.http.HttpSession;

public class ImageHandle {

    private String userFilepath;

    public ImageHandle(HttpSession session) {
        //构造方法，判断用户已经登陆，则修改存放文件的文件夹
        //输出文件夹需要按照用户是否登陆修改
        User user = (User) session.getAttribute("user");
        String id = "visitors";
        if (user != null) {
            id = "" + user.getId();
        }
        //HttpServlet中继承GenericServlet的getInitParameter方法获取的ServletConfig里的配置
        String path = session.getServletContext().getInitParameter("UPLOAD-ROOT");
        userFilepath = path + id + "/";
    }

    /**
     * 通过文件的url获取文件名
     * @param furl 文件的url
     * @return 文件名
     */
    private String getFilename(String furl) {

        return furl.substring(furl.lastIndexOf("\\") + 1);
    }


    /**
     * 将两张图片进行混合
     * @param furl1 作为底图的图片的url
     * @param furl2 用于重叠的图片的url
     * @return 生成图片的url
     */
    public String combine(String furl1, String furl2) {
        //        添加功能：判断如果用户已经登陆，则将图片保存到个人空间（用户单独的文件夹下）
        //        添加功能：将两个图片的大小转化为大致一致

        Image im = new Image(furl1);
        im.combineWithPicture(furl2);

        String filename1 = getFilename(furl1);
        String filename2 = getFilename(furl2);
        String outFilename = filename1.split("\\.")[0] + "-" + filename2.split("\\.")[0] + ".jpg";
        String outFileUrl = this.userFilepath + outFilename;
        im.saveAs(outFileUrl);
        return outFileUrl;
    }

    /**
     * 将图片转化为黑白图片
     * @param furl 需要转换的图片的url
     * @return 生成图片的url
     */
    public String convertToBlockandWhile(String furl) {

        Image im = new Image(furl);
        im.convertToBlackAndWhite();

        String filename = getFilename(furl);
        String outFileUrl = this.userFilepath + filename.split("\\.")[0] + "-B&W.jpg";
        im.saveAs(outFileUrl);
        return outFileUrl;
    }

    /**
     * 按给定的百分比修改图像的大小
     * @param furl              需要改变大小的文件的路径
     * @param percentOfOriginal 需要修改成的文件百分比
     * @return 生成图片的url
     */
    public String resizeByPercent(String furl, int percentOfOriginal) {


        Image im = new Image(furl);
        im.resize(percentOfOriginal);

        String filename = getFilename(furl);
        String outFileUrl = this.userFilepath + filename.split("\\.")[0] + "-ResizeByPer.jpg";
        im.saveAs(outFileUrl);
        return outFileUrl;
    }

    /**
     * 按给定的宽和高修改图片的大小
     * @param furl      需要修改大小的图片路径
     * @param newWidth  修改后的图片宽度
     * @param newHeight 修改后的图片高度
     * @return 生成图片的url
     */
    public String resizeByWidthAndHeight(String furl, int newWidth, int newHeight) {

        Image im = new Image(furl);
        im.resize(newWidth, newHeight);

        String filename = getFilename(furl);
        String outFileUrl = this.userFilepath + filename.split("\\.")[0] + "-ResizeByW&H.jpg";
        im.saveAs(outFileUrl);
        return outFileUrl;
    }

    /**
     * 根据图片的url和选择的类型反转图片
     * @param furl 需要反转的图片路径
     * @param type 反转的类型：若为1，竖直反转；否则，水平反转;
     * @return 生成图片的url
     */
    public String flip(String furl, int type) {

        Image im = new Image(furl);
        String filename = getFilename(furl);
        String outFileUrl = "";

        if (type == 1) {
            im.flipVertically();
            outFileUrl = this.userFilepath + filename.split("\\.")[0] + "-FlipV.jpg";
        } else {
            im.flipHorizontally();
            outFileUrl = this.userFilepath + filename.split("\\.")[0] + "-FlipH.jpg";
        }
        im.saveAs(outFileUrl);
        return outFileUrl;
    }


    /**
     * 将图片按按合适的大小水品重复h列，竖直重复v行
     * @param furl 需要重复的图片路径
     * @param h    水平重复的列数
     * @param v    竖直重复的列数
     * @return 生成图片的url
     */
    public String multiply(String furl, int h, int v) {

        Image im = new Image(furl);
        String filename = getFilename(furl);
        String outFileUrl = "";
        if (h > 0 && v > 0) {
            im.multiply(h, v);
            outFileUrl = this.userFilepath + filename.split("\\.")[0] + "-Multiply" + h + "&" + v + ".jpg";
        } else {
            //提示错误
        }
        im.saveAs(outFileUrl);
        return outFileUrl;
    }

    /**
     * 旋转图片，可以左转90度、右转90度、翻转180度
     * @param furl 需要反转的图片路径
     * @param type 旋转操作的类型：0为左转90度；1为右转90度；2为翻转180度
     * @return 生成图片的url
     */
    public String rotate(String furl, int type) {

        Image im = new Image(furl);
        String filename = getFilename(furl);
        String outFileUrl = "";
        if (type == 0) {
            im.rotateLeft();
            outFileUrl = this.userFilepath + filename.split("\\.")[0] + "-RotateL.jpg";
        } else if (type == 1) {
            im.rotateRight();
            outFileUrl = this.userFilepath + filename.split("\\.")[0] + "-RotateR.jpg";
        } else if (type == 2) {
            im.rotate180();
            outFileUrl = this.userFilepath + filename.split("\\.")[0] + "-Rotate180.jpg";
        }
        im.saveAs(outFileUrl);
        return outFileUrl;
    }

    /**
     * 强调图片的某一部分，其他部分使用半透明黑色遮掩
     * @param furl   需要处理的图像url
     * @param startX 强调区域的左上角x坐标
     * @param startY 强调区域的左上角y坐标
     * @param endX   强调区域的右下角x坐标
     * @param endY   强调区域的右下角y坐标
     * @return 生成图片的url
     */
    public String emphasize(String furl, int startX, int startY, int endX, int endY) {

        Image im = new Image(furl);
        String filename = getFilename(furl);
        String outFileUrl = "";
        int h = im.getHeight();
        int w = im.getWidth();
        if (0 < startX && startX < w && 0 < startY && startY < h && startX < endX && endX < w && startY < endY && endY < h) {
            im.emphasize(startX, startY, endX, endY);
            outFileUrl = this.userFilepath + filename.split(
                    "\\.")[0] + "-Emphasize" + startX + "," + startY + "," + endX + "," + endY + ".jpg";
        } else {
            //错误提示
        }
        im.saveAs(outFileUrl);
        return outFileUrl;
    }

}
