import Image from "next/image";
import { FaRegHeart } from "react-icons/fa";
import vehicles from "../../mocks/vehicles.json";
import { BsFillFuelPumpFill, BsPeopleFill } from "react-icons/bs";
import { TbManualGearboxFilled } from "react-icons/tb";
import { BsFillPeopleFill } from "react-icons/bs";

export default function Home() {
  return (
    <main>
      <div className="px-20 space-x-8">
        <h1 className="mb-4 text-8xl font-extrabold leading-none tracking-tight text-gray-900 mt-10 text-center">
          Premium Vehicle Rental <br /> in Turkiye
        </h1>
        <div className="text-xl pt-6 text-center">
          It is now very easy to rent a car, scooter, motorcycle. The fastest
          and most reliable service where you are!
        </div>
      </div>

      <div className="px-20 mb-32 mt-14">
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
          {vehicles.map((item) => (
            <div
              key={item.id}
              className="h-auto bg-white p-4 shadow-[0_1px_15px_1px_rgba(81,77,92,0.08)]"
            >
              <div className="flex justify-between">
                <div>
                  <div>
                    <a className="text-xl font-semibold text-gray-700">
                      {item.name}
                    </a>
                  </div>
                  <a href="" className="text-gray-400">
                    {item.type}
                  </a>
                </div>
                <div>
                  <FaRegHeart size={18} className="text-gray-400" />
                </div>
              </div>
              <div className="relative w-full h-52">
                <Image
                  src={item.image}
                  layout="fill"
                  objectFit="contain"
                  alt={item.name}
                />
              </div>
              <div className="flex justify-between mt-3 px-4">
                <div className="flex justify-center">
                  <BsFillFuelPumpFill
                    size={18}
                    className="mr-2 text-gray-600"
                  />
                  <a className="text-sm">Gasoline</a>
                </div>
                <div className="flex items-center">
                  <TbManualGearboxFilled size={18} className="text-gray-600" />
                  <a className="text-sm">Automatic</a>
                </div>
                <div className="flex items-center">
                  <BsPeopleFill size={18} className="text-gray-600" />
                  <a className="text-sm">4 Person</a>
                </div>
              </div>
              <div className="flex justify-between px-2 mt-5">
                <div>
                  <a className="text-3xl font-bold">50$/</a>
                  <a className="text-gray-400">day</a> <br />
                  <a className="text-sm font-bold line-through text-gray-500">
                    100$/
                  </a>
                </div>
                <div className="mt-auto mb-auto">
                  <a className="bg-blue-700 text-white rounded-full px-5 py-3">
                    Rent Now!
                  </a>
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>
    </main>
  );
}
