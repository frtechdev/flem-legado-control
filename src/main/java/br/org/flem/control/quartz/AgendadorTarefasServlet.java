package br.org.flem.control.quartz;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import static org.quartz.CronScheduleBuilder.*;
import static org.quartz.JobBuilder.*;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import static org.quartz.TriggerBuilder.*;
import org.quartz.impl.StdSchedulerFactory;

public class AgendadorTarefasServlet extends GenericServlet {

    public void init(ServletConfig config) throws ServletException {
        try {
            
            super.init(config);
            
            Scheduler sched = StdSchedulerFactory.getDefaultScheduler();
            sched.start();
            JobDetail jobDetail = newJob(BloqueioAdJob.class).withIdentity("BloqueioAd", Scheduler.DEFAULT_GROUP).build();
            Trigger trigger = newTrigger().withIdentity("Bloqueio Scheduler").withSchedule(dailyAtHourAndMinute(0, 0)).build();
            sched.scheduleJob(jobDetail, trigger);
            
         
            Scheduler sched1 = StdSchedulerFactory.getDefaultScheduler();
            sched1.start();
            JobDetail jobDetail1 = newJob(CriarUsuarioJob.class).withIdentity("CriadorUsuario", Scheduler.DEFAULT_GROUP).build();
            Trigger trigger1 = newTrigger().withIdentity("Criador Usuario").withSchedule(dailyAtHourAndMinute(13,30)).build();
            sched1.scheduleJob(jobDetail1, trigger1);
      
            
        } catch (SchedulerException ex) {
            Logger.getLogger(AgendadorTarefasServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void service(ServletRequest arg0, ServletResponse arg1) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}