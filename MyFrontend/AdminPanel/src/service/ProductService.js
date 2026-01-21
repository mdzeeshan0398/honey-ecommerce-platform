import axios from 'axios';

const API_URL = 'http://localhost:8080/api/products';

export const addProduct = async (productData,imageUrl) => {
 
        const formData = new FormData();
         formData.append('product',JSON.stringify(productData));
         formData.append('file',imageUrl);
         try{
                await axios.post(API_URL,formData,{
                headers:{"Content-Type":"multipart/form-data"}
            });
         }catch(error){
          console.log('Error',error);
          throw error;
         }
}
export const getProductList = async () => {
    try{
        const response = await axios.get(API_URL);
        console.log("Product Api response",response.data);
        return response.data;
    }catch(error){
        console.log('Error fetching product',error);
        throw error;
    }
}
export const deleteProducts = async (productId) => {
    try{
      const response = await axios.delete(`${API_URL}/${productId}`);
      return response.status === 200 || response.status === 204;
    }catch(error){
        console.log('Error while fetching the food',error);
        throw error;
    }
  }