import Hero from "@/components/Hero";
import Slider3D from "@/components/Slider3D";
import StudentsBanner from "@/components/StudentsBanner";
import Features from "@/components/Features";
import Stats from "@/components/Stats";
import CTA from "@/components/CTA";

const Index = () => {
  return (
    <div className="min-h-screen bg-background overflow-x-hidden">
      {/* Animated gradient background for entire page */}
      <div className="fixed inset-0 -z-10">
        <div className="absolute inset-0 bg-gradient-to-br from-[#5453E0]/5 via-background to-[#5453E0]/10" />
        <div className="absolute top-0 left-1/4 w-[800px] h-[800px] bg-[#5453E0]/20 rounded-full blur-[120px] animate-pulse-slow" />
        <div className="absolute bottom-0 right-1/4 w-[600px] h-[600px] bg-[#5453E0]/15 rounded-full blur-[100px] animate-pulse-slow" style={{ animationDelay: '2s' }} />
      </div>
      
      <Hero />
      <Slider3D />
      <StudentsBanner />
      <Features />
      <Stats />
      <CTA />
    </div>
  );
};

export default Index;
