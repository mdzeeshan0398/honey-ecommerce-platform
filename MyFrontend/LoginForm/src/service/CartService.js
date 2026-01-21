import axios from 'axios'


const API_URL = "http://localhost:8080/api/cart";

export const addToCart = async (productId,token) => {
    try{
        await axios.post(
        API_URL,
        { productId },
        {
          headers: {
          Authorization: `Bearer ${token}`
        },
      },
    );
    }catch(error){
        console.log('Error while adding in cart data',error);
    }
}
export const removeCartData = async (productId,token) => {
    try{
        await axios.post(
        API_URL+'/remove',
      { productId },
      {
        headers: {
          Authorization: `Bearer ${token}`
        },
      },
    );
    }catch(error){
        console.log('Error while removing from cart',error);

    }
}
export const getCartData = async (token) => {
    try{
        const response = await axios.get(API_URL, {
        headers: {
        Authorization: `Bearer ${token}` },
    });
        return response.data.item || {};
    }catch(error){
        console.log('Error while fetching cart data',error);
        return {};
    }
}