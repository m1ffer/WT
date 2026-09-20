package edu.epam.fop.web;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebListener;

import java.util.EnumSet;

@WebListener
public class ApplicationInitializer implements ServletContextListener {
    public static final String CALCULATOR_URL = "/CalculatorServlet";

    private static void addDefaultFilterMapping(FilterRegistration.Dynamic filter){
        filter.addMappingForUrlPatterns(
                EnumSet.of(DispatcherType.REQUEST),
                false,
                CALCULATOR_URL
        );
    }

    @Override
    public void contextInitialized(ServletContextEvent sce){
        ServletContext context = sce.getServletContext();
        context.setInitParameter(
                "expression-regex",
                "^(?<operand1>[-+]?[0-9]*\\.?[0-9]+)(?<operation>[-+*/])(?<operand2>[-+]?[0-9]*\\.?[0-9]+)$"
        );
        context.setRequestCharacterEncoding("UTF-8");
        context.setResponseCharacterEncoding("UTF-8");

        ServletRegistration.Dynamic calculatorServlet =
                context.addServlet("CalculatorServlet",
                        new CalculatorServlet());
        calculatorServlet.addMapping(CALCULATOR_URL);
        calculatorServlet.setLoadOnStartup(1);

        FilterRegistration.Dynamic encodingFilter =
                context.addFilter("EncodingFilter",
                        new EncodingFilter());
        encodingFilter.setInitParameter("encoding", "UTF-8");
        addDefaultFilterMapping(encodingFilter);

        FilterRegistration.Dynamic loggingFilter =
                context.addFilter("LoggingFilter",
                        new LoggingFilter());
        addDefaultFilterMapping(loggingFilter);

        FilterRegistration.Dynamic validationFilter =
                context.addFilter("ValidationFilter",
                        new ValidationFilter());
        addDefaultFilterMapping(validationFilter);
    }
}
