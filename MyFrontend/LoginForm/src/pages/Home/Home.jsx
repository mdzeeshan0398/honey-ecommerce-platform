import React, { useState } from 'react';
import Header from '../../components/Header/Header';
import ExploreMenu from '../../components/ExploreMenu/ExploreMenu';
import HoneyDisplay from '../../components/HoneyDisplay/HoneyDisplay';

const Home = () => {
  const [category,setCategory] = useState('All');
  return (
    <main className='container'>
        <Header/>
        <ExploreMenu category={category} setCategory={setCategory}/>
        <HoneyDisplay category={category} searchText={''}/>
    </main>
    
  )
}

export default Home;