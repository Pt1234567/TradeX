
import { Button } from './components/ui/button'
import Navbar from './page/Navbar/Navbar'
import Home from './page/Home/Home'
import Portfolio from './page/Portfolio/Portfolio'
import Activity from './page/Activity/Activity'
import Wallet from './page/Wallet/Wallet'
import { Route, Routes } from 'react-router-dom'
import Withdrawal from './page/Withdrawal/Withdrawal'
import PaymentDetails from './page/PaymentDetails/PaymentDetails'
import StockDetails from './page/StockDetails/StockDetails'
import WatchList from './page/WatchList/WatchList'
import Profile from './page/Profile/Profile'
import SearchCoin from './page/SearchCoin/SearchCoin'
import NotFound from './page/NotFound/NotFound'

function App() {


  return (
    <>
      <Navbar/>
      <Routes>
        <Route path='/' element={<Home/>}></Route>
        <Route path='/portfolio' element={<Portfolio/>}></Route>
        <Route path='/activity' element={<Activity/>}></Route>
        <Route path='/wallet' element={<Wallet/>}></Route>
        <Route path='/withdrawal' element={<Withdrawal/>}></Route>
        <Route path='/paymentDetails' element={<PaymentDetails/>}></Route>
        <Route path='/market/:id' element={<StockDetails/>}></Route>
        <Route path='/watchlist' element={<WatchList/>}></Route>
        <Route path='/profile' element={<Profile/>}></Route>
        <Route path='/search' element={<SearchCoin/>}></Route>
        <Route path='/*' element={<NotFound/>}></Route>
      </Routes>
    </>
  )
}

export default App
