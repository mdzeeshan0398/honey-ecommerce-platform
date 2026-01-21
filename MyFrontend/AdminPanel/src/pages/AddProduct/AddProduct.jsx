import React from "react";
import { assets } from "../../assets/assets";
import { useState } from "react";
import { addProduct } from "../../service/ProductService";
import { toast } from "react-toastify";

const AddProduct = () => {
  const [image, setImage] = useState(false);
  const [data, setData] = useState({
    name: "",
    description: "",
    price: "",
    category: "Black Forest",
  });
  const onChangeHandler = (event) => {
    const name = event.target.name;
    const value = event.target.value;
    setData((data) => ({ ...data, [name]: value }));
  };
  const onSubmitHandler = async (event) => {
    event.preventDefault();
    if (!image) {
      toast.error("Please select an image");
      return;
    }
    try {
      await addProduct(data, image);
      toast.success("Product added successfully");
      setData({
        name: "",
        description: "",
        category: "Black Forest",
        price: "",
      });
      setImage(null);
    } catch (error) {
      toast.error("Error adding food");
    }
  };
  return (
    <div className="mx-2 mt-2">
      <div className="row">
        <div className="card col-md-4">
          <div className="card-body">
            <h2 className="mb-4">Add Product</h2>
            <form onSubmit={onSubmitHandler}>
              <div className="mb-3">
                <label htmlFor="image" className="form-label">
                  <img
                    src={image ? URL.createObjectURL(image) : assets.upload}
                    alt=""
                    width={98}
                  />
                </label>
                <input
                  type="file"
                  className="form-control"
                  id="image"
                  hidden
                  onChange={(e) => setImage(e.target.files[0])}
                />
              </div>
              <div className="mb-3">
                <label htmlFor="name" className="form-label">
                  Name
                </label>
                <input
                  type="text"
                  placeholder="Honey"
                  className="form-control"
                  id="name"
                  required
                  name="name"
                  onChange={onChangeHandler}
                  value={data.name}
                />
              </div>

              <div className="mb-3">
                <label htmlFor="description" className="form-label">
                  Description
                </label>
                <textarea
                  className="form-control"
                  placeholder="Write Content"
                  id="description"
                  rows="5"
                  required
                  name="description"
                  onChange={onChangeHandler}
                  value={data.description}
                ></textarea>
              </div>
              <div className="mb-3">
                <label htmlFor="category" className="form-label">
                  Category
                </label>
                <select
                  name="category"
                  id="category"
                  className="form-control"
                  onChange={onChangeHandler}
                  value={data.category}
                >
                  <option value="Ajwain">Ajwain Honey</option>
                  <option value="Black Forest">Black Forest Honey</option>
                  <option value="Wild Forest">Wild Forest Honey</option>
                  <option value="Ginger">Ginger Honey</option>
                  <option value="Acacia">Acacia Honey</option>
                  <option value="Moringa">Moringa Honey</option>
                  <option value="Tulsi">Tulsi Honey</option>
                  <option value="Jamun">Jamun Honey</option>
                  <option value="Kashmiri">Kashmiri Honey</option>
                  <option value="Mustard">Mustard Honey</option>
                </select>
              </div>
              <div className="mb-3">
                <label htmlFor="price" className="form-label">
                  Price
                </label>
                <input
                  type="number"
                  name="price"
                  id="price"
                  placeholder="&#8377;200"
                  className="form-control"
                  onChange={onChangeHandler}
                  value={data.price}
                />
              </div>
              <button type="submit" className="btn btn-primary">
                Save
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AddProduct;
