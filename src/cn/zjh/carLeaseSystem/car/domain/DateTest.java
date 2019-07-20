package cn.zjh.carLeaseSystem.car.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTest {
	public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        //跨年不会出现问题
        //如果时间为：2016-03-18 11:59:59 和 2016-03-19 00:00:01的话差值为 0
        Date fDate=sdf.parse("2015-12-31");
        Date oDate=sdf.parse("2015-01-01");
        long days=(oDate.getTime()-fDate.getTime())/(1000*3600*24);
        System.out.print(days);
	}
}
