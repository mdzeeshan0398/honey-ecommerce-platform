import React from "react";
import { useState } from "react";
import { toast } from "react-toastify";
import { useEffect } from "react";
import "./ListProduct.css";
import { deleteProducts, getProductList } from "../../service/ProductService";

const ListProduct = () => {
  const [list, setList] = useState([]);
  const fetchList = async () => {
    try {
      const data = await getProductList();
      setList(data || []);
      console.log("PRODUCT API RESPONSE 👉", data);
    } catch (error) {
      toast.error("Error while reading product");
    }
  };
  const handleDelete = async (productId) => {
    try {
      const success = await deleteProducts(productId);
      if (success) {
        toast.success("Product removed");
        fetchList();
      } else {
        toast.error("Error occured while removing");
      }
    } catch (error) {
      toast.error("Error occured while removing");
    }
  };
  useEffect(() => {
    fetchList();
  }, []);

  return (
    <div className="py-5 row justify-content-center">
      <div className="col-11 card">
        <table className="table">
          <thead>
            <tr>
              <th>Image</th>
              <th>Name</th>
              <th>Category</th>
              <th>Price</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {list.length === 0 ? (
              <tr>
                <td colSpan="5" className="text-center">
                  No products found
                </td>
              </tr>
            ) : (
              list.map((item) => (
                <tr key={item.id}>
                  <td>
                    <img
                      src={item.imageUrl}
                      alt={item.name}
                      width="48"
                      height="48"
                    />
                  </td>
                  <td>{item.name}</td>
                  <td>{item.category}</td>
                  <td>&#8377;{item.price}</td>
                  <td className="text-danger">
                    <i
                      className="bi bi-x-circle-fill"
                      style={{ cursor: "pointer" }}
                      onClick={() => handleDelete(item.id)}
                    ></i>
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default ListProduct;
