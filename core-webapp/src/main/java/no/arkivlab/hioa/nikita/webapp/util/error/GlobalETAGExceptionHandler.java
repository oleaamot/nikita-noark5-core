package no.arkivlab.hioa.nikita.webapp.util.error;

import nikita.util.CommonUtils;
import nikita.util.exceptions.NikitaETAGMalformedHeaderException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by tsodring on 5/11/17.
 */
@ControllerAdvice
class GlobalETAGExceptionHandler {

    /**
     * The point of this class is to capture exceptions that occur outside a controller.
     * <p>
     * This is related to https://github.com/HiOA-ABI/nikita-noark5-core/issues/76
     **/

    @ExceptionHandler(value = NikitaETAGMalformedHeaderException.class)
    public ResponseEntity<String> defaultErrorHandler(HttpServletRequest request, Exception ex) throws Exception {
        final String message = ex.getMessage() == null ? ex.getClass().getSimpleName() : ex.getMessage();
        final String devMessage = ExceptionUtils.getRootCauseMessage(ex);
        final String devStackTrace = ex.toString();

        ApiError apiError = new ApiError(Integer.parseInt(HttpStatus.BAD_REQUEST.toString()), message, devMessage, devStackTrace);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(apiError.toJSON());
    }
}
