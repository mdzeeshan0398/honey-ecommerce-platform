import React from 'react';
import HoneyDisplay from '../../components/HoneyDisplay/HoneyDisplay';
import { useState } from 'react';

const ExploreHoney = () => {
  const [category,setCategory] = useState('All');
  const [searchText,setSearchText] = useState('');
  return (
   <>
    <div className="container">
      <div className="row justify-content-center">
        <div className="col-md-6">
          <form onSubmit={(e) => e.preventDefault()}>
            <div className="input-group mb-3">
              <select className='form-select mt-2' style={{'maxWidth' : '150px'}} onChange={(e) => setCategory(e.target.value)}>
              <option value="All">All</option>
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
              <input type="text" className='form-control mt-2' placeholder = 'Search your favorite honey...' 
                onChange={(e) => setSearchText(e.target.value)} value={searchText}/>
              <button className='btn btn-primary mt-2' type='submit'>
                <i className='bi bi-search'></i>
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
     <HoneyDisplay  category={category} searchText={searchText}/>
   </>
  )
}

export default ExploreHoney;