package com.fuar.config.log;

import com.fuar.model.log.LogRestIncomingRequest;
import com.fuar.service.log.LogRestIncomingService;
import com.fuar.util.Utils;
import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Random;

@ComponentScan("packageToScan")
@Configurable
public class LoggingInterceptor implements HandlerInterceptor {

    private static final String RS_START_TIME = "rs.incoming.start.time";
    private static final String RS_REQUEST_ID = "rs.incoming.request.id";

    Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Autowired
    private LogRestIncomingService service;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        try {
            LogRestIncomingRequest restIncomingRequest = new LogRestIncomingRequest();
            String bound = "incoming message";
            restIncomingRequest.setId(new Random().nextLong());

            long diff = -1;
            long endTime = System.currentTimeMillis();
            Long startTime = Long.valueOf(endTime);
            restIncomingRequest.setStartTime(new Date(startTime));
            request.setAttribute(RS_START_TIME, startTime);

            String restRequestId = Utils.generateKey();
            restIncomingRequest.setRestRequestId(restRequestId);
            request.setAttribute(RS_REQUEST_ID, restRequestId);

        //    restIncomingRequest.setContentPayload(payload);
            //   if(log.isInfoEnabled()) {
            String endpoint = (String) request.getRequestURI();
            restIncomingRequest.setEndpointUrl(endpoint);
            //}

            restIncomingRequest.setHttpType(request.getMethod());
            restIncomingRequest.setOperationType(bound);
            restIncomingRequest.setElapsedTime(diff);
            //restIncomingRequest.setServerIp();
            //ThreadContext.put("requestid", UUID.randomUUID().toString());

            StringBuilder sb = new StringBuilder();
            restIncomingRequest.setContentPayload(sb.toString());
/*
            InputStream inputStream = null;
            BufferedReader reader = null;
            try {
                inputStream = request.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally
            {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
*/
            service.save(restIncomingRequest).subscribe(result -> logger.debug("Entity has been saved: {}", result));

        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

        ThreadContext.clearAll();
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}