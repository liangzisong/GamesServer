package com.mangni.vegaplan.toolshelper;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;

public class LogAspect {  
  
    private final static Log log = LogFactory.getLog(LogAspect.class); 
  
    /**  
     * 前置通知 在某连接点之前执行的通知，但这个通知不能阻止连接点前的执行  
     * @param joinPoint 连接点：程序执行过程中的某一行为  
     */  
    public void beforMethod(JoinPoint joinPoint)throws IOException{  
        String methodName = joinPoint.getSignature().getName();  
        List<Object> args = Arrays.asList(joinPoint.getArgs());  
        WriteErrorToLog4J("beforMethod is exexuting the method :"+methodName+", and the params of the method are：<"+ args+">");  
        System.out.println("beforMethod is exexuting the method : "+methodName+", and the params of the method are：<"+ args+">");  
    }  
  
    /**  
     * 后置通知（无论方法是否发生异常都会执行,所以访问不到方法的返回值）  
     * @param joinPoint  
     */  
    public void afterMethod(JoinPoint joinPoint)throws IOException{  
        String methodName = joinPoint.getSignature().getName();  
        WriteErrorToLog4J("afterMethod is exexuting the method ： " + methodName);  
        System.out.println("afterMethod is exexuting the method ： " + methodName);  
  
    }  
    /**  
     * 返回通知（在方法正常结束执行的代码）  
     * 返回通知可以访问到方法的返回值！  
     * @param joinPoint  
     */  
    public void afterReturnMethod(JoinPoint joinPoint,Object result)throws IOException{  
        String methodName = joinPoint.getSignature().getName();  
        WriteErrorToLog4J("afterMethod is exexuting the method : "+methodName+",and returns the result is <"+result+">");  
        System.out.println("afterMethod is exexuting the method : "+methodName+",and returns the result is <"+result+">");  
    }  
  
    /**  
     * 异常通知（方法发生异常执行的代码）  
     * 可以访问到异常对象；且可以指定在出现特定异常时执行的代码,使用在方法aspect()上注册的切入点  
     * @param joinPoint  
     * @param ex  
     */  
    public void afterThrow(JoinPoint joinPoint, Exception ex){  
        String methodName = joinPoint.getSignature().getName();  
  
        //判断日志输出级别  
        if(log.isInfoEnabled()){  
            log.info("异常通知： " + joinPoint + "\t" + ex.getMessage());  
        }  
        //详细错误信息  
        String errorMsg = "";  
        StackTraceElement[] trace = ex.getStackTrace();  
        for (StackTraceElement s : trace) {  
            errorMsg += "\tat " + s + "\r\n";  
        }  
        System.out.println("具体异常信息："+errorMsg);  
  
        System.out.println("产生异常的方法名为： " + joinPoint + "\t" + ex.getMessage());  
  
        System.out.println("异常信息结束！！！");  
  
        //写入异常日志  
        writeLog(errorMsg,joinPoint,ex);  
  
    }  
  
    //    把正常信息信息写进日志里面  
    public void WriteErrorToLog4J(String message)throws IOException {  
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        try {  
            //FileWriter fw = new FileWriter(filePath, true);//如果追加方式用true
        	Writer writer = new BufferedWriter(  
        	        new OutputStreamWriter(  
        	                new FileOutputStream(Bean.getLogfilepath(),true),"UTF-8"));
            //BufferedWriter bw = new BufferedWriter(writer);  
  
            writer.append("-----------"+sdf.format(new Date())+"------------\r\n");// 往已有的文件上添加字符串  
            writer.append(message+"\n ");  
  
            writer.flush();  
            writer.close();  
            writer.close();  
        } catch (Exception e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
    }  
    /**  
     *  
     * @param detailErrMsg 详细错误信息  
     * @param joinPoint 方法名称  
     * @Description: 日志异常  
     * @author： Ma  
     * @createTime： 2016-10-14  
     */  
    public void writeLog(String detailErrMsg,JoinPoint joinPoint,Exception ex){  
  
        String cla=joinPoint.getTarget().getClass().getName();//action  
        String method=joinPoint.getSignature().getName();//method  
        try{  
  
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
            //创建输出异常log日志  
//            File file =new File(filePath);  
//            //if file doesnt exists, then create it  
//            if(!file.exists()){  
//                file.createNewFile();  
//            }  
            FileWriter fw = new FileWriter(Bean.getLogfilepath(), true);  
            BufferedWriter bw = new BufferedWriter(fw);  
  
//            FileOutputStream out=new FileOutputStream(file,false);  
            //日志具体参数  
            StringBuffer sb=new StringBuffer();  
            sb.append("-----------"+sdf.format(new Date())+"------------\r\n");  
            sb.append("接口方法：["+cla+"."+method+"]\r\n");  
            sb.append("详细错误信息："+ex+"\r\n");  
            sb.append(detailErrMsg+"\r\n");  
  
//            sb.toString().getBytes("utf-8")  
            bw.append(new String(sb.toString().getBytes("utf-8"))+"\r\n");//注意需要转换对应的字符集  
            bw.close();
            fw.close();
            
            String exceptionmessage;
    		exceptionmessage="\r\n"+ex.getMessage();
    		StackTraceElement[] stpara=ex.getStackTrace();
    		for(StackTraceElement st:stpara)
    		{
    			exceptionmessage+="\r"+st.getClassName()+"("+st.getLineNumber()+")";
    		}
    		MyJdbcTemplate myJdbcTemplate=(MyJdbcTemplate) Bean.getCtx().getBean("myJdbcTemplate");
    		String sql="INSERT INTO errlog(loglv,playerid,statement,classname,methodname,logmes) VALUES(?,?,?,?,?,?)";
    		myJdbcTemplate.update(sql,new Object[]{"WARN","0",detailErrMsg,exceptionmessage,"dopost",ex.getMessage()});
    		Date nowdate=new Date();		
    		SimpleDateFormat dateFormat = Bean.getDateFormat();
    		String time=dateFormat.format(nowdate.getTime());
    		Bean.getTextArea().append("\r\n");
    		Bean.getTextArea().append(time+"："+exceptionmessage+"\r");
    		Bean.getTextArea().setCaretPosition(Bean.getTextArea().getText().length());
    		ex.printStackTrace();
        }catch(IOException e){  
            e.printStackTrace();  
        }  
  
  
    }  
  
}  