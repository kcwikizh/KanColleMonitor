/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.web.security.controller.impl;

import java.lang.management.CompilationMXBean;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.List;
import java.util.Map;
import kcwiki.x.kcscanner.cache.inmem.AppDataCache;
import static kcwiki.x.kcscanner.tools.ConstantValue.LINESEPARATOR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author iHaru
 */
@RestController
@RequestMapping(value = {"/debug","/Debug"}, produces = "text/html;charset=UTF-8")
public class Debug {
    static final Logger LOG = LoggerFactory.getLogger(Debug.class);
    
    private StringBuilder sb = null;
    
    @RequestMapping("/showinfo")
    public String showinfo(@RequestParam(value="password", defaultValue="World") String password) {
        if(!password.equals("password"))
            return "Fail";
        getDubugInfo();
        return sb.toString();
    }
    
    private void getDubugInfo() {
            sb = new StringBuilder();
            this.addString(LINESEPARATOR);
            this.addString("各项指针参数： ");
            this.addString("isAppInit： " + AppDataCache.isAppInit);
            this.addString("isReadyReceive： " + AppDataCache.isReadyReceive);
            this.addString("isScanTaskSuspend： " + AppDataCache.isScanTaskSuspend);
            this.addString("isDownloadShipVoice： " + AppDataCache.isDownloadShipVoice);
//            Jedis redis = JedisPoolUtils.getJedis();
//            if (redis == null) {
//                this.addString("redis.ping： " + "Null");
//            } else {
//                this.addString("redis.ping： " + JedisPoolUtils.getJedis().ping());
//                redis.close();
//            }
            
            this.addString(LINESEPARATOR);
            this.addString("各项实时数据： ");
            this.addString("kcHost： " + AppDataCache.kcHost);
            this.addString("start2data： " + AppDataCache.start2data);
            this.addString("systemScanEntitys： " );
            AppDataCache.systemScanEntitys.forEach((k, v) -> {
                this.addString("Name: " + v.getName() + "\t Lastmodified: " + v.getLastmodified());
            });
            this.addString("gameWorlds： " );
            AppDataCache.gameWorlds.forEach((k, v) -> {
                this.addString("key: " + k + "\t data: " + v);
            });
            this.addString("stringCache： " );
            AppDataCache.stringCache.forEach((k, v) -> {
                this.addString("key: " + k + "\t data: " + v);
            });
            this.addString("worldVersionCache： " );
            AppDataCache.worldVersionCache.forEach((k, v) -> {
                this.addString("key: " + k + "\t data: " + v);
            });
            this.addString("maintenanceInfo： " );
            AppDataCache.maintenanceInfo.forEach((k, v) -> {
                this.addString("key: " + k + "\t data: " + v);
            });
            this.addString("dataHashCache： " );
            AppDataCache.dataHashCache.forEach((k, v) -> {
                this.addString("key: " + k + "\t data: " + v);
            });
            this.addString("existTables： " );
            AppDataCache.existTables.forEach(k -> {
                this.addString("name: " + k);
            });
            this.addString("responseCache： " );
            AppDataCache.responseCache.forEach((k, v) -> {
                this.addString("key: " + k + "\t data: " + v);
                this.addString(LINESEPARATOR);
            });
            this.addString(LINESEPARATOR);
            this.addString("后台统计： ");
            ThreadMXBean tmxb = ManagementFactory.getThreadMXBean();
            ThreadInfo[] threadInfos = tmxb.dumpAllThreads(false, false);
            for (ThreadInfo info : threadInfos) {
                this.addString("[" + info.getThreadId() + "] " + info.getThreadName());
            }
            this.addString("threadInfos: "+threadInfos.length);
            this.addString(LINESEPARATOR);
            Map<Thread, StackTraceElement[]> AllStackTraces = Thread.getAllStackTraces();
            int count = 0;
            for (Thread t : AllStackTraces.keySet()) {
                StackTraceElement[] stes = AllStackTraces.get(t);
                for (StackTraceElement ste:stes) {
                    if(ste.getClassName().contains("kcwiki.x.kcscanner")){
                        count++;
                        this.addString("className: "+ste.getClassName()+"\tmethodName: "+ste.getMethodName()+"\tisNativeMethod: "+ste.isNativeMethod());
                    }
                }
            }
            this.addString("KcWikiStackTraces: "+count);
            
            this.addString(LINESEPARATOR);
            this.addString("核心测试项： ");
            MemoryMXBean memorymbean = ManagementFactory.getMemoryMXBean();   
            MemoryUsage usage = memorymbean.getHeapMemoryUsage();   
            this.addString("INIT HEAP: " + usage.getInit());   
            this.addString("MAX HEAP: " + usage.getMax());   
            this.addString("USE HEAP: " + usage.getUsed());   
            this.addString("\nFull Information:");   
            this.addString("Heap Memory Usage: "   
            + memorymbean.getHeapMemoryUsage());   
            this.addString("Non-Heap Memory Usage: "   
            + memorymbean.getNonHeapMemoryUsage());   

            List<String> inputArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();   
            this.addString("===================获取java运行参数信息=============== "); 
            inputArguments.forEach((String s) -> {
                this.addString(s);
            });

            this.addString("=======================通过java来获取相关系统状态============================ ");  
            double i = (double)Runtime.getRuntime().totalMemory()/1024;//Java 虚拟机中的内存总量,以字节(Byte)为单位  
            this.addString("总的内存量 ： "+i);  
            double j = (double)Runtime.getRuntime().freeMemory()/1024;//Java 虚拟机中的空闲内存量  
            this.addString("空闲内存量 ： "+j);  
            this.addString("最大内存量 ： "+Runtime.getRuntime().maxMemory()/1024);  

            this.addString("=======================获取操作系统相关信息============================ ");  
            OperatingSystemMXBean osm = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();  

            //获取操作系统相关信息  
            this.addString("osm.getArch() "+osm.getArch());  
            this.addString("osm.getAvailableProcessors() "+osm.getAvailableProcessors());  
            this.addString("osm.getSystemLoadAverage() "+osm.getSystemLoadAverage());   
            this.addString("osm.getName() "+osm.getName());  
            this.addString("osm.getVersion() "+osm.getVersion());  
            //获取整个虚拟机内存使用情况  
            this.addString("=======================获取整个虚拟机内存使用情况============================ ");  
            MemoryMXBean mm=(MemoryMXBean)ManagementFactory.getMemoryMXBean();  
            this.addString("getHeapMemoryUsage "+mm.getHeapMemoryUsage());  
            this.addString("getNonHeapMemoryUsage "+mm.getNonHeapMemoryUsage());  
            //获取各个线程的各种状态，CPU 占用情况，以及整个系统中的线程状况  
            this.addString("=======================获取各个线程的各种状态============================ ");  
            ThreadMXBean tm=(ThreadMXBean)ManagementFactory.getThreadMXBean();  
            this.addString("getThreadCount "+tm.getThreadCount());  
            this.addString("getPeakThreadCount "+tm.getPeakThreadCount());  
            this.addString("getCurrentThreadCpuTime "+tm.getCurrentThreadCpuTime());  
            this.addString("getDaemonThreadCount "+tm.getDaemonThreadCount());  
            this.addString("getCurrentThreadUserTime "+tm.getCurrentThreadUserTime());  

            //当前编译器情况  
            this.addString("=======================当前编译器情况============================ ");  
            CompilationMXBean gm=(CompilationMXBean)ManagementFactory.getCompilationMXBean();  
            this.addString("getName "+gm.getName());  
            this.addString("getTotalCompilationTime "+gm.getTotalCompilationTime());  

            //获取多个内存池的使用情况  
            this.addString("=======================获取多个内存池的使用情况============================ ");  
            List<MemoryPoolMXBean> mpmList=ManagementFactory.getMemoryPoolMXBeans();  
            for(MemoryPoolMXBean mpm:mpmList){  
                this.addString("getUsage "+mpm.getUsage());   
                String[] MemoryManagerNames =mpm.getMemoryManagerNames();
                for(String MemoryManagerName:MemoryManagerNames){
                    this.addString("MemoryManagerName ： "+MemoryManagerName);
                }
            }  
            //获取GC的次数以及花费时间之类的信息  
            this.addString("=======================获取GC的次数以及花费时间之类的信息============================ ");  
            List<GarbageCollectorMXBean> gcmList=ManagementFactory.getGarbageCollectorMXBeans();  
            for(GarbageCollectorMXBean gcm:gcmList){  
                this.addString("getName "+gcm.getName());  
                String[] getMemoryPoolNames = gcm.getMemoryPoolNames();
                for(String getMemoryPoolName:getMemoryPoolNames){
                    this.addString("getMemoryPoolName ： "+getMemoryPoolName);
                }
            }  
            //获取运行时信息  
            this.addString("=======================获取运行时信息============================ ");  
            RuntimeMXBean rmb=(RuntimeMXBean)ManagementFactory.getRuntimeMXBean();  
            this.addString("getClassPath "+rmb.getClassPath());  
            this.addString("getLibraryPath "+rmb.getLibraryPath());  
            this.addString("getVmVersion "+rmb.getVmVersion());  
            this.addString(LINESEPARATOR);
            this.addString(LINESEPARATOR); 


              this.addString(LINESEPARATOR);
              this.addString("AllStackTraces： ");
              count=0;
              for (Thread t : AllStackTraces.keySet()) {
                StackTraceElement[] stes = AllStackTraces.get(t);
                for (StackTraceElement ste:stes) {
                    count++;
                    this.addString("className: "+ste.getClassName()+"\tmethodName: "+ste.getMethodName()+"\tisNativeMethod: "+ste.isNativeMethod());
                }
            }
                this.addString("StackTracesArray.size()： "+count);

              this.addString(LINESEPARATOR);
              this.addString(LINESEPARATOR);
              this.addString(LINESEPARATOR);
    }
    
    private void addString(String str) {
      sb.append(str + LINESEPARATOR + "</br>");
    }
    
}
