/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kcwiki.x;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author x5171
 */
public class DrawChart {
    private static final Logger LOG = LoggerFactory.getLogger(DrawChart.class);
    private ChartPanel pieFrame;  
    private ChartPanel ringFrame;  
    private ChartPanel xyFrame;
    private ChartPanel barFrame;
    
    String tempfolder = System.getProperty("java.io.tmpdir");
    String tmpchart = String.format("%s%s", tempfolder, "tmpchart/");
    
    
    public JFreeChart getPieChart(DefaultPieDataset data, String title){  
//          DefaultPieDataset data = getPieDataSet();  
          JFreeChart chart = ChartFactory.createPieChart(title,data,true,false,false);  
        //设置百分比  
          PiePlot pieplot = (PiePlot) chart.getPlot();  
          DecimalFormat df = new DecimalFormat("0.00%");//获得一个DecimalFormat对象，主要是设置小数问题  
          NumberFormat nf = NumberFormat.getNumberInstance();//获得一个NumberFormat对象  
          StandardPieSectionLabelGenerator sp1 = new StandardPieSectionLabelGenerator("{0}  {2}", nf, df);//获得StandardPieSectionLabelGenerator对象  
          pieplot.setLabelGenerator(sp1);//设置饼图显示百分比  
        
      //没有数据的时候显示的内容  
          pieplot.setNoDataMessage("无数据显示");  
          pieplot.setCircular(false);  
          pieplot.setLabelGap(0.02D);  
        
          pieplot.setIgnoreNullValues(true);//设置不显示空值  
          pieplot.setIgnoreZeroValues(true);//设置不显示负值  
         pieFrame=new ChartPanel (chart,true);  
          chart.getTitle().setFont(new Font("宋体",Font.BOLD,20));//设置标题字体  
          PiePlot piePlot= (PiePlot) chart.getPlot();//获取图表区域对象  
          piePlot.setLabelFont(new Font("宋体",Font.BOLD,10));//解决乱码  
          chart.getLegend().setItemFont(new Font("黑体",Font.BOLD,10));  
          return chart;
    }
    
    public JFreeChart getRingChart(DefaultPieDataset data, String title){  
//          DefaultPieDataset data = getPieDataSet();  
          JFreeChart chart = ChartFactory.createRingChart(title,data,true,false,false);  
        //设置百分比  
          PiePlot pieplot = (PiePlot) chart.getPlot();  
          DecimalFormat df = new DecimalFormat("0.00%");//获得一个DecimalFormat对象，主要是设置小数问题  
          NumberFormat nf = NumberFormat.getNumberInstance();//获得一个NumberFormat对象  
          StandardPieSectionLabelGenerator sp1 = new StandardPieSectionLabelGenerator("{0}  {2}", nf, df);//获得StandardPieSectionLabelGenerator对象  
          pieplot.setLabelGenerator(sp1);//设置饼图显示百分比  
        
      //没有数据的时候显示的内容  
          pieplot.setNoDataMessage("无数据显示");  
          pieplot.setCircular(false);  
          pieplot.setLabelGap(0.02D);  
        
          pieplot.setIgnoreNullValues(true);//设置不显示空值  
          pieplot.setIgnoreZeroValues(true);//设置不显示负值  
         ringFrame=new ChartPanel (chart,true);  
          chart.getTitle().setFont(new Font("宋体",Font.BOLD,20));//设置标题字体  
          PiePlot piePlot= (PiePlot) chart.getPlot();//获取图表区域对象  
          piePlot.setLabelFont(new Font("宋体",Font.BOLD,10));//解决乱码  
          chart.getLegend().setItemFont(new Font("黑体",Font.BOLD,10));  
          return chart;
    }
    
    public JFreeChart getTimeSeriesChart(XYDataset xydataset, String title, String xTitle, String yTitle){  
//        XYDataset xydataset = getXYDataset();  
        JFreeChart jfreechart = ChartFactory.createTimeSeriesChart(title, xTitle, yTitle,xydataset, true, true, true);  
        XYPlot xyplot = (XYPlot) jfreechart.getPlot();  
        DateAxis dateaxis = (DateAxis) xyplot.getDomainAxis();  
        dateaxis.setDateFormatOverride(new SimpleDateFormat("MM-yyyy"));  
        xyFrame=new ChartPanel(jfreechart,true);  
        dateaxis.setLabelFont(new Font("黑体",Font.BOLD,14));         //水平底部标题  
        dateaxis.setTickLabelFont(new Font("宋体",Font.BOLD,12));  //垂直标题  
        ValueAxis rangeAxis=xyplot.getRangeAxis();//获取柱状  
        rangeAxis.setLabelFont(new Font("黑体",Font.BOLD,15));  
        jfreechart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 15));  
        jfreechart.getTitle().setFont(new Font("宋体",Font.BOLD,20));//设置标题字体  
        return jfreechart;
    }   
    
    public JFreeChart getBarChart(CategoryDataset dataset, String title, String xTitle, String yTitle){  
//        CategoryDataset dataset = getDataSet();  
        JFreeChart chart = ChartFactory.createBarChart(
                             title, // 图表标题  
                            xTitle, // 目录轴的显示标签  
                            yTitle, // 数值轴的显示标签  
                            dataset, // 数据集  
                            PlotOrientation.VERTICAL, // 图表方向：水平、垂直  
                            true,           // 是否显示图例(对于简单的柱状图必须是false)  
                            true,          // 是否生成工具  
                            false           // 是否生成URL链接  
                            );  
        
        CategoryItemRenderer renderer = ((CategoryPlot)chart.getPlot()).getRenderer();
        renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setDefaultItemLabelsVisible(true);
//        ItemLabelPosition position = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, 
//                TextAnchor.TOP_CENTER);
//        renderer.setDefaultPositiveItemLabelPosition(position);
          
        //从这里开始  
        CategoryPlot plot=chart.getCategoryPlot();//获取图表区域对象  
        CategoryAxis domainAxis=plot.getDomainAxis();         //水平底部列表  
         domainAxis.setLabelFont(new Font("黑体",Font.BOLD,14));         //水平底部标题  
         domainAxis.setTickLabelFont(new Font("宋体",Font.BOLD,12));  //垂直标题  
         ValueAxis rangeAxis=plot.getRangeAxis();//获取柱状  
         rangeAxis.setLabelFont(new Font("黑体",Font.BOLD,15));  
          chart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 15));  
          chart.getTitle().setFont(new Font("宋体",Font.BOLD,20));//设置标题字体  
            
          //到这里结束，虽然代码有点多，但只为一个目的，解决汉字乱码问题  
            
         barFrame=new ChartPanel(chart,true);        //这里也可以用chartFrame,可以直接生成一个独立的Frame  
        return chart;
    }  
    
    private CategoryDataset getBarDataSet() {  
           DefaultCategoryDataset dataset = new DefaultCategoryDataset();  
           dataset.addValue(100, "北京", "苹果");  
           dataset.addValue(100, "上海", "苹果");  
           dataset.addValue(100, "广州", "苹果");  
           dataset.addValue(200, "北京", "梨子");  
           dataset.addValue(200, "上海", "梨子");  
           dataset.addValue(200, "广州", "梨子");  
           dataset.addValue(300, "北京", "葡萄");  
           dataset.addValue(300, "上海", "葡萄");  
           dataset.addValue(300, "广州", "葡萄");  
           dataset.addValue(400, "北京", "香蕉");  
           dataset.addValue(400, "上海", "香蕉");  
           dataset.addValue(400, "广州", "香蕉");  
           dataset.addValue(500, "北京", "荔枝");  
           dataset.addValue(500, "上海", "荔枝");  
           dataset.addValue(500, "广州", "荔枝");  
           return dataset;  
}  
    
     private XYDataset getXYDataset() {  //这个数据集有点多，但都不难理解  
            TimeSeries timeseries = new TimeSeries("legal & general欧洲指数信任");  
            timeseries.add(new Month(2, 2001), 181.80000000000001D);  
            timeseries.add(new Month(3, 2001), 167.30000000000001D);  
            timeseries.add(new Month(4, 2001), 153.80000000000001D);  
            timeseries.add(new Month(5, 2001), 167.59999999999999D);  
            timeseries.add(new Month(6, 2001), 158.80000000000001D);  
            timeseries.add(new Month(7, 2001), 148.30000000000001D);  
            timeseries.add(new Month(8, 2001), 153.90000000000001D);  
            timeseries.add(new Month(9, 2001), 142.69999999999999D);  
            timeseries.add(new Month(10, 2001), 123.2D);  
            timeseries.add(new Month(11, 2001), 131.80000000000001D);  
            timeseries.add(new Month(12, 2001), 139.59999999999999D);  
            timeseries.add(new Month(1, 2002), 142.90000000000001D);  
            timeseries.add(new Month(2, 2002), 138.69999999999999D);  
            timeseries.add(new Month(3, 2002), 137.30000000000001D);  
            timeseries.add(new Month(4, 2002), 143.90000000000001D);  
            timeseries.add(new Month(5, 2002), 139.80000000000001D);  
            timeseries.add(new Month(6, 2002), 137D);  
            timeseries.add(new Month(7, 2002), 132.80000000000001D);  
            TimeSeries timeseries1 = new TimeSeries("legal & general英国指数信任");  
            timeseries1.add(new Month(2, 2001), 129.59999999999999D);  
            timeseries1.add(new Month(3, 2001), 123.2D);  
            timeseries1.add(new Month(4, 2001), 117.2D);  
            timeseries1.add(new Month(5, 2001), 124.09999999999999D);  
            timeseries1.add(new Month(6, 2001), 122.59999999999999D);  
            timeseries1.add(new Month(7, 2001), 119.2D);  
            timeseries1.add(new Month(8, 2001), 116.5D);  
            timeseries1.add(new Month(9, 2001), 112.7D);  
            timeseries1.add(new Month(10, 2001), 101.5D);  
            timeseries1.add(new Month(11, 2001), 106.09999999999999D);  
            timeseries1.add(new Month(12, 2001), 110.3D);  
            timeseries1.add(new Month(1, 2002), 111.7D);  
            timeseries1.add(new Month(2, 2002), 111D);  
            timeseries1.add(new Month(3, 2002), 109.59999999999999D);  
            timeseries1.add(new Month(4, 2002), 113.2D);  
            timeseries1.add(new Month(5, 2002), 111.59999999999999D);  
            timeseries1.add(new Month(6, 2002), 108.8D);  
            timeseries1.add(new Month(7, 2002), 101.59999999999999D);  
            TimeSeriesCollection timeseriescollection = new TimeSeriesCollection();  
            timeseriescollection.addSeries(timeseries);  
            timeseriescollection.addSeries(timeseries1);  
            return timeseriescollection;  
        } 
    
    private DefaultPieDataset getPieDataSet() {  
        DefaultPieDataset dataset = new DefaultPieDataset();  
        dataset.setValue("苹果",100);  
        dataset.setValue("梨子",200);  
        dataset.setValue("葡萄",300);  
        dataset.setValue("香蕉",400);  
        dataset.setValue("荔枝",500);  
        return dataset;  
    }  
    
    public static void saveAsFile(JFreeChart chart, String outputPath,      
                int weight, int height)throws Exception {   

        BufferedImage image = new BufferedImage(weight, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();

        g2.setRenderingHint(JFreeChart.KEY_SUPPRESS_SHADOW_GENERATION, true);
        Rectangle r = new Rectangle(0, 0, 600, 400);
        chart.draw(g2, r);
        File f = new File(outputPath);

        BufferedImage chartImage = chart.createBufferedImage( 600, 400, null); 
        ImageIO.write( chartImage, "png", f );          
    }
    
    public static void main(String args[]) throws Exception{  
        DrawChart drawChart = new DrawChart();
        JFrame frame=new JFrame("Java数据统计图");  
        frame.setLayout(new GridLayout(2,2,10,10));  
        DrawChart.saveAsFile(drawChart.getBarChart(drawChart.getBarDataSet(), "title", "x", "y"), "C:\\Users\\x5171\\Desktop\\freemarker\\1.png", 600, 400);
        DrawChart.saveAsFile(drawChart.getPieChart(drawChart.getPieDataSet(), "title"), "C:\\Users\\x5171\\Desktop\\freemarker\\2.png", 600, 400);
        DrawChart.saveAsFile(drawChart.getTimeSeriesChart(drawChart.getXYDataset(), "title", "x", "y"), "C:\\Users\\x5171\\Desktop\\freemarker\\3.png", 600, 400);
        drawChart.getRingChart(drawChart.getPieDataSet(), "title");
        frame.add(drawChart.getBarFrame());           //添加柱形图  
        frame.add(drawChart.getPieFrame());           //添加饼状图  
        frame.add(drawChart.getXyFrame());    //添加折线图  
        frame.add(drawChart.getRingFrame());
        frame.setBounds(50, 50, 800, 600);  
        frame.setVisible(true);  
    }  

    /**
     * @return the pieFrame
     */
    public ChartPanel getPieFrame() {
        return pieFrame;
    }

    /**
     * @return the xyFrame
     */
    public ChartPanel getXyFrame() {
        return xyFrame;
    }

    /**
     * @return the barFrame
     */
    public ChartPanel getBarFrame() {
        return barFrame;
    }

    /**
     * @return the ringFrame
     */
    public ChartPanel getRingFrame() {
        return ringFrame;
    }
}
