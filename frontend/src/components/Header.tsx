"use client";
import { FaBars, FaSign } from "react-icons/fa";
import { LiaSignInAltSolid } from "react-icons/lia";

import { useState } from "react";
import Image from "next/image";

const Header: React.FC = () => {
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const menuItems = [
    {
      key: 1,
      text: "Home",
      href: "/",
    },
    {
      key: 2,
      text: "Vehicles",
      href: "/",
    },
    {
      key: 3,
      text: "About Us",
      href: "/",
    },
  ];
  return (
    <header className="bg-[#fefeff]">
      <div className="container mx-auto px-18 py-4 flex justify-between items-center">
        {/* Logo */}
        <Image src="/images/logo.svg" alt="Logo" width={100} height={59} />

        {/* Menü (Masaüstü için) */}
        <nav className="hidden md:flex space-x-10 font-bold">
          {menuItems.map((item, index) => (
            <a
              key={item.key}
              href={item.href}
              className="text-black text-lg mt-2"
            >
              {item.text}
            </a>
          ))}

          <a className="flex items-center space-y-2 text-white bg-black px-4 py-2 cursor-pointer rounded-full">
            Sign In <LiaSignInAltSolid size={24} className="ml-3" />
          </a>
        </nav>

        {/* Hamburger Menü (Mobil için) */}
        <div className="md:hidden">
          <button onClick={() => setIsMenuOpen(!isMenuOpen)}>
            <FaBars size={24} />
          </button>
        </div>
      </div>

      {/* Mobil Menü */}
      {isMenuOpen && (
        <nav className="md:hidden bg-white p-4 space-y-4 space-x-6">
          {menuItems.map((item, index) => (
            <a key={item.key} href={item.href} className="hover:text-gray-800">
              {item.text}
            </a>
          ))}
        </nav>
      )}
    </header>
  );
};

export default Header;
