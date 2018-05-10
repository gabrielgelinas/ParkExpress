<%-- 
    Document   : payment
    Created on : 7-May-2018, 10:48:07 AM
    Author     : gggab
--%>
<%
    int row = (int) request.getAttribute("row");
    int col = (int) request.getAttribute("col");
%>


<div class="container text-center">
    <span class="h2">
        Paiement de parking : 15.00 $
    </span>
    <br>
    row : <%= row%>
    col : <%= col%>
    <hr>
    <form action="Serv_Payment" method="get">
        <input type="submit" name="submit" value="submit">
    </form>

    The good Button: 
    <div id="paypal-button-container"></div>
    -----------
</div>
    
    <div class="hidden">
        <form id="paymentcomplete" action="Serv_Payment" method="post">
            <input type="hidden" name="parking_spot_row" value="<%= row%>" >
            <input type="hidden" name="parking_spot_col" value="<%= col%>" >
        </form>
        <form id="paymentcancel" action="Serv_Payment" method="get">
            
        </form>
    </div>

<script>
    paypal.Button.render({

        env: 'sandbox', // sandbox | production

        // PayPal Client IDs - replace with your own
        // Create a PayPal app: https://developer.paypal.com/developer/applications/create
        client: {
            sandbox: 'AXoy6zsNdSjrKFkz-JKs-Pyyzi8JbV0yw5uu3RPkGho3OIlNajVSJmau6lTWJqK2V3uDX8NO0Pof5FPK',
            production: 'AXoy6zsNdSjrKFkz-JKs-Pyyzi8JbV0yw5uu3RPkGho3OIlNajVSJmau6lTWJqK2V3uDX8NO0Pof5FPK'
        },
        // Show the buyer a 'Pay Now' button in the checkout flow
        commit: true,
        // payment() is called when the button is clicked
        payment: function (data, actions) {

            // Make a call to the REST api to create the payment
            return actions.payment.create({
                intent: "sale",
                payer: {
                    payment_method: "paypal"
                },
                transactions: [
                    {
                        amount: {
                            total: "15.00",
                            currency: "CAD",
                            details: {
                                subtotal: "15.00"
                            }
                        },
                        description: "Payment Parking",
                        soft_descriptor: "Parking",
                        item_list: {
                            items: [
                                {
                                    name: "Parking Spot",
                                    description: "spot : " +<%= row%> + "0" +<%= col%>,
                                    quantity: "1",
                                    price: "15",
                                    sku: "1",
                                    currency: "CAD"
                                }
                            ]
                        }
                    }
                ],
                note_to_payer: "Contact us for any questions on your order."
            });
        },
        // onAuthorize() is called when the buyer approves the payment
        onAuthorize: function (data, actions) {

            // Make a call to the REST api to execute the payment
            return actions.payment.execute().then(function () {
                window.alert('Payment Complete!');
                document.getElementById('paymentcomplete').submit();
            });
        }

    }, '#paypal-button-container');

</script>