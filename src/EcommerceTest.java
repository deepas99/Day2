
import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

import POJO.Login;
import POJO.Orders;
import POJO.addedProd;
import POJO.getOrderId;
import POJO.loginResPayload;
import POJO.orderDetails;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class EcommerceTest {
	public static void main(String[] args) {
		Login l = new Login();
		l.setUserEmail("dpasss@gmail.com");
		l.setUserPassword("12345@Ds");
		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON).build();
		RequestSpecification reqlogin = given().log().all().relaxedHTTPSValidation().spec(req).body(l);
		
		loginResPayload l1 = reqlogin.when().post("/api/ecom/auth/login").then().log().all().extract().response().as(loginResPayload.class);
		System.out.println(l1.getToken());
		System.out.println(l1.getUserId());
		String token = l1.getToken();
		String name = l1.getUserId();
		
		//add prod
		RequestSpecification addProd = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("authorization",token).build();
		RequestSpecification addProdReq = given().relaxedHTTPSValidation().log().all().spec(addProd).param("productName", "LapTopdbks").param("productAddedBy", name).param("productCategory","fashion").param("productSubCategory", "shirts").param("productPrice", "11500")
		.param("productDescription","Addias Originals").param("productFor", "women").multiPart("productImage",new File("C:\\Users\\deepas\\Documents\\Postman\\Image//Capture.png"));
		addedProd p = addProdReq.when().post("/api/ecom/product/add-product").then().log().all().extract().response().as(addedProd.class);
				String productId = p.getProductId();
		System.out.println(productId);	
		
		//create order
		RequestSpecification createOrder = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("authorization",token).setContentType(ContentType.JSON).build();
		orderDetails orderdetail = new orderDetails();
		orderdetail.setCountry("India");
		orderdetail.setProductOrderedId(productId);
		List<orderDetails> orderDetailList = new ArrayList<orderDetails>();
		orderDetailList.add(orderdetail);
		Orders orders = new Orders();
		orders.setOrders(orderDetailList);
		RequestSpecification createOrderReq = given().relaxedHTTPSValidation().log().all().spec(createOrder).body(orders);
		String responseAddOrder = createOrderReq.when().post("/api/ecom/order/create-order").then().log().all().extract().response().asString();
				//as(getOrderId.class);
		JsonPath js = new JsonPath(responseAddOrder);
		List<String> ordersId = js.get("orders");
		String actOrderId = ordersId.get(0);
		System.out.println(ordersId);
		System.out.println(actOrderId);
		
//		deleteAddedproduct
		RequestSpecification delProdReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("authorization",token).setContentType(ContentType.JSON).build();
		
		RequestSpecification delprod = given().relaxedHTTPSValidation().log().all().spec(delProdReq).pathParam("productId", productId);
		String delResponse = delprod.when().delete("/api/ecom/product/delete-product/{productId}").then().log().all().extract().response().asString();
		System.out.println(delResponse);
		JsonPath js1 = new JsonPath(delResponse);
		Assert.assertEquals("Product Deleted Successfully", js1.get("message"));
////		
//	
//		//deleteOrder
		//RequestSpecification delOrderReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("authorization",token).setContentType(ContentType.JSON).build();
		RequestSpecification delOrder = given().relaxedHTTPSValidation().log().all().spec(delProdReq).pathParam("orderId", actOrderId);
		String delOrderResp = delOrder.when().delete("/api/ecom/order/delete-order/{orderId}").then().log().all().extract().response().asString();
		JsonPath js2 = new JsonPath(delOrderResp);
		Assert.assertEquals("Orders Deleted Successfully", js2.get("message"));
		
	}
}
