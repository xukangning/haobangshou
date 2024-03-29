package com.hbs.common.josn;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.StrutsConstants;
import org.apache.struts2.StrutsStatics;

import com.hbs.common.josn.annotations.SMD;
import com.hbs.common.josn.annotations.SMDMethod;
import com.hbs.common.josn.annotations.SMDMethodParameter;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * <!-- START SNIPPET: description -->
 *
 * This result serializes an action into JSON.
 *
 * <!-- END SNIPPET: description -->
 *
 * <p/> <u>Result parameters:</u>
 *
 * <!-- START SNIPPET: parameters -->
 *
 * <ul>
 *
 * <li>excludeProperties - list of regular expressions matching the properties to be excluded.
 * The regular expressions are evaluated against the OGNL expression representation of the properties. </li>
 *
 * </ul>
 *
 * <!-- END SNIPPET: parameters -->
 *
 * <b>Example:</b>
 *
 * <pre><!-- START SNIPPET: example -->
 * &lt;result name="success" type="json" /&gt;
 * <!-- END SNIPPET: example --></pre>
 *
 */
public class JSONResult implements Result {
    private static final long serialVersionUID = 8624350183189931165L;
    private static final Log log = LogFactory.getLog(JSONResult.class);
    private String defaultEncoding = "ISO-8859-1";
    private List<Pattern> includeProperties;
    private List<Pattern> excludeProperties;
    private String root;
    private boolean wrapWithComments;
    private boolean enableSMD = false;
    private boolean enableGZIP = false;
    private boolean ignoreHierarchy = false;
    private boolean ignoreInterfaces = true;
    
    // 增加contentType参数（支持ajax上载文件） 2008.06.26 modified
    private String contentType = null;
    private boolean enumAsBean = JSONWriter.ENUM_AS_BEAN_DEFAULT;

    @Inject(StrutsConstants.STRUTS_I18N_ENCODING)
    public void setDefaultEncoding(String val) {
        this.defaultEncoding = val;
    }

    /**
     * Gets a list of regular expressions of properties to exclude
     * from the JSON output.
     *
     * @return A list of compiled regular expression patterns
     */
    public List<Pattern> getExcludePropertiesList() {
        return this.excludeProperties;
    }

    /**
     * Sets a comma-delimited list of regular expressions to match
     * properties that should be excluded from the JSON output.
     *
     * @param commaDelim A comma-delimited list of regular expressions
     */
    public void setExcludeProperties(String commaDelim) {
        List<String> excludePatterns = JSONUtil.asList(commaDelim);
        if (excludePatterns != null) {
            this.excludeProperties = new ArrayList<Pattern>(excludePatterns.size());
            for (String pattern : excludePatterns) {
                this.excludeProperties.add(Pattern.compile(pattern));
            }
        }
    }

    /**
     * @return the includeProperties
     */
    public List<Pattern> getIncludePropertiesList() {
        return includeProperties;
    }

    /**
     * @param includedProperties the includeProperties to set
     */
    @SuppressWarnings("unchecked")
	public void setIncludeProperties(String commaDelim) {
        List<String> includePatterns = JSONUtil.asList(commaDelim);
        if (includePatterns != null) {
            this.includeProperties = new ArrayList<Pattern>(includePatterns.size());

            HashMap existingPatterns = new HashMap();

            for (String pattern : includePatterns) {
                // Compile a pattern for each *unique* "level" of the object
                // hierarchy specified in the regex.
                String[] patternPieces = pattern.split("\\\\\\.");

                String patternExpr = "";
                for (String patternPiece : patternPieces) {
                    if (patternExpr.length() > 0) {
                        patternExpr += "\\.";
                    }
                    patternExpr += patternPiece;

                    // Check for duplicate patterns so that there is no overlap.
                    if (!existingPatterns.containsKey(patternExpr)) {
                        existingPatterns.put(patternExpr, patternExpr);

                        // Add a pattern that does not have the indexed property matching (ie. list\[\d+\] becomes list).
                        if (patternPiece.endsWith("\\]")) {
                            this.includeProperties.add(Pattern.compile(patternExpr.substring(0, patternPiece.lastIndexOf("\\["))));

                            if (log.isDebugEnabled())
                                log.debug("Adding include property expression:  " + patternExpr.substring(0, patternPiece.lastIndexOf("\\[")));
                        }

                        this.includeProperties.add(Pattern.compile(patternExpr));

                        if (log.isDebugEnabled())
                            log.debug("Adding include property expression:  " + patternExpr);
                    }
                }
            }
        }
    }

    public void execute(ActionInvocation invocation) throws Exception {
        ActionContext actionContext = invocation.getInvocationContext();
        HttpServletRequest request = (HttpServletRequest) actionContext
            .get(StrutsStatics.HTTP_REQUEST);
        HttpServletResponse response = (HttpServletResponse) actionContext
            .get(StrutsStatics.HTTP_RESPONSE);

        try {
            String json;
            Object rootObject;
            if (this.enableSMD) {
                //generate SMD
                rootObject = this.writeSMD(invocation);
            } else {
                // generate JSON
                if (this.root != null) {
                    ValueStack stack = invocation.getStack();
                    rootObject = stack.findValue(this.root);
                } else {
                    rootObject = invocation.getAction();
                }
            }
            json = JSONUtil.serialize(rootObject, excludeProperties, includeProperties, ignoreHierarchy, enumAsBean);

            boolean writeGzip = enableGZIP && JSONUtil.isGzipInRequest(request);

            writeToResponse(response, json, writeGzip, contentType);

        } catch (IOException exception) {
            log.error(exception.getMessage(), exception);
            throw exception;
        }
    }

    protected void writeToResponse(HttpServletResponse response,
                                   String json, boolean gzip, String contenttType) throws IOException {
        JSONUtil.writeJSONToResponse(response, getEncoding(),
            isWrapWithComments(), json, false, gzip, contenttType);
    }

    @SuppressWarnings("unchecked")
    private com.hbs.common.josn.smd.SMD writeSMD(ActionInvocation invocation) {
        ActionContext actionContext = invocation.getInvocationContext();
        HttpServletRequest request = (HttpServletRequest) actionContext
            .get(StrutsStatics.HTTP_REQUEST);

        //root is based on OGNL expression (action by default)
        Object rootObject = null;
        if (this.root != null) {
            ValueStack stack = invocation.getStack();
            rootObject = stack.findValue(this.root);
        } else {
            rootObject = invocation.getAction();
        }

        Class clazz = rootObject.getClass();
        com.hbs.common.josn.smd.SMD smd = new com.hbs.common.josn.smd.SMD();
        //URL
        smd.setServiceUrl(request.getRequestURI());

        //customize SMD
        SMD smdAnnotation = (SMD) clazz.getAnnotation(SMD.class);
        if (smdAnnotation != null) {
            smd.setObjectName(smdAnnotation.objectName());
            smd.setServiceType(smdAnnotation.serviceType());
            smd.setVersion(smdAnnotation.version());
        }

        //get public methods
        Method[] methods = JSONUtil.listSMDMethods(clazz, ignoreInterfaces);

        for (Method method : methods) {
            SMDMethod smdMethodAnnotation = method.getAnnotation(SMDMethod.class);

            //SMDMethod annotation is required
            if (((smdMethodAnnotation != null) && !this.shouldExcludeProperty(method
                .getName()))) {
                String methodName = smdMethodAnnotation.name().length() == 0 ? method
                    .getName() : smdMethodAnnotation.name();

                    com.hbs.common.josn.smd.SMDMethod smdMethod = new com.hbs.common.josn.smd.SMDMethod(
                    methodName);
                smd.addSMDMethod(smdMethod);

                //find params for this method
                int parametersCount = method.getParameterTypes().length;
                if (parametersCount > 0) {
                    Annotation[][] parameterAnnotations = method
                        .getParameterAnnotations();

                    for (int i = 0; i < parametersCount; i++) {
                        //are you ever going to pick shorter names? nope
                        SMDMethodParameter smdMethodParameterAnnotation = this
                            .getSMDMethodParameterAnnotation(parameterAnnotations[i]);

                        String paramName = smdMethodParameterAnnotation != null ? smdMethodParameterAnnotation
                            .name()
                            : "p" + i;

                        //goog thing this is the end of the hierarchy, oitherwise I would need that 21'' LCD ;)
                        smdMethod
                            .addSMDMethodParameter(new com.hbs.common.josn.smd.SMDMethodParameter(
                                paramName));
                    }
                }

            } else {
                if (log.isDebugEnabled())
                    log.debug("Ignoring property " + method.getName());
            }
        }
        return smd;
    }

    /**
     * Find an SMDethodParameter annotation on this array
     */
    private com.hbs.common.josn.annotations.SMDMethodParameter getSMDMethodParameterAnnotation(
        Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            if (annotation instanceof com.hbs.common.josn.annotations.SMDMethodParameter)
                return (com.hbs.common.josn.annotations.SMDMethodParameter) annotation;
        }

        return null;
    }

    private boolean shouldExcludeProperty(String expr) {
        if (this.excludeProperties != null) {
            for (Pattern pattern : this.excludeProperties) {
                if (pattern.matcher(expr).matches())
                    return true;
            }
        }
        return false;
    }

    /**
     * Retrieve the encoding
     * <p/>
     *
     * @return The encoding associated with this template (defaults to the value of 'struts.i18n.encoding' property)
     */
    protected String getEncoding() {
        String encoding = this.defaultEncoding;

        if (encoding == null) {
            encoding = System.getProperty("file.encoding");
        }

        if (encoding == null) {
            encoding = "UTF-8";
        }

        return encoding;
    }

    /**
     * @return  OGNL expression of root object to be serialized
     */
    public String getRoot() {
        return this.root;
    }

    /**
     * Sets the root object to be serialized, defaults to the Action
     *
     * @param root OGNL expression of root object to be serialized
     */
    public void setRoot(String root) {
        this.root = root;
    }

    /**
     * @return Generated JSON must be enclosed in comments
     */
    public boolean isWrapWithComments() {
        return this.wrapWithComments;
    }

    /**
     * Wrap generated JSON with comments
     * @param wrapWithComments
     */
    public void setWrapWithComments(boolean wrapWithComments) {
        this.wrapWithComments = wrapWithComments;
    }

    /**
     * @return Result has SMD generation enabled
     */
    public boolean isEnableSMD() {
        return this.enableSMD;
    }

    /**
     * Enable SMD generation for action, which can be used for JSON-RPC
     * @param enableSMD
     */
    public void setEnableSMD(boolean enableSMD) {
        this.enableSMD = enableSMD;
    }

    public void setIgnoreHierarchy(boolean ignoreHierarchy) {
        this.ignoreHierarchy = ignoreHierarchy;
    }

    /**
     * Controls whether interfaces should be inspected for method annotations
     * You may need to set to this true if your action is a proxy as annotations on methods are not inherited
     */
    public void setIgnoreInterfaces(boolean ignoreInterfaces) {
        this.ignoreInterfaces = ignoreInterfaces;
    }

    /**
     * Controls how Enum's are serialized :
     *    If true, an Enum is serialized as a name=value pair (name=name()) (default)
     *    If false, an Enum is serialized as a bean with a special property _name=name()
     *
     * @param enumAsBean
     */
    public void setEnumAsBean(boolean enumAsBean) {
        this.enumAsBean = enumAsBean;
    }

    public boolean isEnableGZIP() {
        return enableGZIP;
    }

    public void setEnableGZIP(boolean enableGZIP) {
        this.enableGZIP = enableGZIP;
    }

    public void setContentType(String contentType) {
        if (contentType != null) {
            this.contentType = contentType.trim();
        }
    }
}
