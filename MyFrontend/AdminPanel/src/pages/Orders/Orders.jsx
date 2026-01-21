import React, { useEffect, useState } from "react";
import axios from "axios";
import { assets } from "../../assets/assets";
import { toast } from "react-toastify";
const Orders = () => {
  const [data, setData] = useState([]);
  const fetchOrders = async () => {
    try {
      const response = await axios.get("http://localhost:8080/api/orders/all");
      setData(response.data || []);
    } catch (error) {
      toast.error("Failed to fetch orders".error);
    }
  };
  const updateStatus = async (event, orderId) => {
    try {
      const response = await axios.patch(
        `http://localhost:8080/api/orders/status/${orderId}?status=${event.target.value}`,
      );
      if (response.status === 200 || response.status === 201) {
        fetchOrders();
      }
    } catch (error) {
      toast.error("Failed to update order status", error);
    }
  };
  useEffect(() => {
    fetchOrders();
  }, []);
  return (
    <div className="container">
      <div className="py-5 row justify-content-center">
        <div className="col-11 card">
          <table className="table table-responsive">
            <tbody>
              {data.map((order) => {
                return (
                  <tr key={order.id}>
                    <td>
                      <img src={assets.parcel} alt="" height={48} width={48} />
                    </td>
                    <td>
                      <div>
                        {(order.orderItems || []).length === 0
                          ? "No items"
                          : order.orderItems.map((item, index) => (
                              <span key={item.id}>
                                {item.name || "Product"} x {item.quantity}
                                {index !== order.orderItems.length - 1 && ", "}
                              </span>
                            ))}
                      </div>
                      <div>{order.userAddress};</div>
                    </td>
                    <td>&#8377;{order.amount.toFixed(2)}</td>
                    <td>Items:{(order.orderItems || []).length}</td>

                    <td>
                      <select
                        name=""
                        id=""
                        className="form-control"
                        onChange={(event) => updateStatus(event, order.id)}
                        value={order.orderStatus}
                      >
                        <option value="Honey Preparing">Honey Preparing</option>
                        <option value="Out For Delivery">
                          Out For Delivery
                        </option>
                        <option value="Delivered">Delivered</option>
                      </select>
                    </td>
                  </tr>
                );
              })}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
};

export default Orders;
