package api.resources;

import api.contracts.payments.GetMyHistoryPaymetsRequest;
import api.contracts.payments.GetMyHistoryPaymetsResponse;
import api.contracts.payments.GetPaymentInfoRequest;
import api.contracts.payments.GetPaymentInfoResponse;
import api.handlers.payments.GetMyHistoryPamentsHandler;
import api.handlers.payments.GetPaymentInfoHandler;
import api.handlers.utilities.StatusResolver;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Api(value = "payments")
@Path("/payments")
public class PaymentsResource {
    @Inject
    private GetPaymentInfoHandler getPaymentInfoHandler;
    @Inject
    private GetMyHistoryPamentsHandler getMyHistoryPamentsHandler;

    @GET
    @Produces("application/json")
    @Path("{paymentId}")
    @ApiOperation(value = "Get payment info", response = GetPaymentInfoResponse.class)
    public Response getPaymentInfo(@PathParam("paymentId") int paymentId) {
        GetPaymentInfoRequest request = new GetPaymentInfoRequest();

        request.PaymentId = paymentId;

        GetPaymentInfoResponse response = getPaymentInfoHandler.handle(request);

        int statusCode = StatusResolver.getStatusCode(response);

        return Response.status(statusCode).entity(response).build();
    }

    @GET
    @Produces("application/json")
    @Path("my/history")
    @ApiOperation(value = "Get mybpayments", response = GetMyHistoryPaymetsResponse.class)
    public Response getMyHistoryPaments() {
        GetMyHistoryPaymetsRequest request = new GetMyHistoryPaymetsRequest();

        GetMyHistoryPaymetsResponse response = getMyHistoryPamentsHandler.handle(request);

        int statusCode = StatusResolver.getStatusCode(response);

        return Response.status(statusCode).entity(response).build();
    }
}
